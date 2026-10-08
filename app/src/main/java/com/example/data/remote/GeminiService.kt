package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

enum class AiMode(val modelName: String, val title: String, val description: String) {
    BALANCED("gemini-3.5-flash", "Dengeli Empati", "Hızlı ve kapsamlı duygusal rehberlik (3.5 Flash)"),
    LITE("gemini-3.1-flash-lite-preview", "Düşük Gecikme (Lite)", "Anında hızlı rahatlama ve sakinleşme (3.1 Lite)"),
    DEEP_THINKING("gemini-3.1-pro-preview", "Derin Düşünme (Pro)", "Karmaşık akran çatışması ve derin çözümleme (High Thinking)")
}

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "USER" or "AI"
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val modeUsed: AiMode = AiMode.BALANCED,
    val isThinking: Boolean = false
)

object GeminiApiClient {
    private const val TAG = "GeminiApiClient"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private const val SYSTEM_PROMPT = """
Sen 'Empati Köprüsü' platformunun şefkatli, sakinleştirici ve anlayışlı psikolojik iyi oluş ve empati yapay zeka rehberisin.
Görevin:
1. Okul ortamındaki öğrencilere, öğretmenlere ve ailelere güvenli, yargılamayan, samimi ve empatik bir alan sağlamak.
2. Akran zorbalığı, sınav stresi, yalnızlık, sınır çizme, 'hayır' diyebilme ve iletişim problemlerinde yapıcı stratejiler sunmak.
3. Çocukların ve gençlerin duygularını tanımasına, nefes almasına ve sakinleşmesine rehberlik etmek.
4. Acil durumlarda (kendine zarar verme, şiddetli tehlike) mutlaka okul rehberlik servisine (PDR), öğretmenlere veya güvenilir bir yetişkine başvurmasını sıcak bir dille öğütlemek.
5. Asla tıbbi reçete veya kesin klinik tanı koyma; her zaman destekleyici, dinleyici ve güçlendirici bir dil kullan.
6. Yanıtların sakinleştirici, anlaşılır, Türkçe ve yeşil huzur temasını yansıtan nazik bir tonda olsun.
"""

    suspend fun sendMessage(
        history: List<ChatMessage>,
        userMessage: String,
        mode: AiMode
    ): Result<String> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Provide intelligent, warm empathy fallback response
            return@withContext Result.success(getEmpatheticFallback(userMessage, mode))
        }

        try {
            val endpoint = "$BASE_URL${mode.modelName}:generateContent?key=$apiKey"

            val jsonBody = JSONObject().apply {
                // System Instruction
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", SYSTEM_PROMPT) })
                    })
                })

                // Contents history
                val contentsArray = JSONArray()

                // Include last 6 messages for context
                val recentHistory = history.takeLast(6)
                for (msg in recentHistory) {
                    val role = if (msg.sender == "USER") "user" else "model"
                    contentsArray.put(JSONObject().apply {
                        put("role", role)
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply { put("text", msg.text) })
                        })
                    })
                }

                // Add current message
                contentsArray.put(JSONObject().apply {
                    put("role", "user")
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", userMessage) })
                    })
                })
                put("contents", contentsArray)

                // Generation Config
                val genConfig = JSONObject().apply {
                    put("temperature", if (mode == AiMode.DEEP_THINKING) 0.4 else 0.7)
                    if (mode == AiMode.DEEP_THINKING) {
                        put("thinkingConfig", JSONObject().apply {
                            put("thinkingLevel", "HIGH")
                        })
                    }
                }
                put("generationConfig", genConfig)
            }

            val request = Request.Builder()
                .url(endpoint)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (!response.isSuccessful || responseBody.isNullOrBlank()) {
                Log.w(TAG, "Gemini API error code: ${response.code}, body: $responseBody")
                return@withContext Result.success(getEmpatheticFallback(userMessage, mode))
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")

            val replyText = buildString {
                if (parts != null) {
                    for (i in 0 until parts.length()) {
                        val part = parts.optJSONObject(i)
                        val text = part?.optString("text")
                        if (!text.isNullOrEmpty()) {
                            append(text)
                        }
                    }
                }
            }

            if (replyText.isNotBlank()) {
                Result.success(replyText.trim())
            } else {
                Result.success(getEmpatheticFallback(userMessage, mode))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Gemini call exception", e)
            Result.success(getEmpatheticFallback(userMessage, mode))
        }
    }

    private fun getEmpatheticFallback(message: String, mode: AiMode): String {
        val lower = message.lowercase()
        return when {
            lower.contains("zorba") || lower.contains("alay") || lower.contains("dışla") -> {
                "Seni çok iyi anlıyorum ve hissettiklerinde tamamen haklısın. Kimsenin seni değersiz hissettirmeye, alay etmeye veya sınırlarını ihlal etmeye hakkı yok.\n\n" +
                "🌿 Şu an yapabileceğin adımlar:\n" +
                "1. Karşı tarafın tepkilerini üzerine alma; bu onların kendi içsel eksiklikleriyle ilgili.\n" +
                "2. 'Bana bu şekilde davranmandan hoşlanmıyorum' diyerek net bir sınır çiz.\n" +
                "3. Uygulamamızdaki 'Güvenli Destek İste' butonu ile okul rehberlik öğretmenine tek tıkla haber verebilirsin. Asla yalnız değilsin."
            }
            lower.contains("kaygı") || lower.contains("kork") || lower.contains("stres") || lower.contains("sınav") -> {
                "Şu an hissettiğin endişe çok doğal, beynin seni korumaya çalışıyor. Ama kontrolün sende olduğunu hatırla.\n\n" +
                "🍃 Birlikte küçük bir rahatlama yapalım:\n" +
                "Burnundan derin bir nefes al (4 saniye)... Tut (4 saniye)... Ağzından yavaşça ver (6 saniye).\n" +
                "Bir sınav veya tek bir an senin kim olduğunu ve değerini belirlemez. Kendine nazik davran."
            }
            lower.contains("yalnız") || lower.contains("kimse") || lower.contains("arkadaş") -> {
                "Bazen kalabalıkların ortasında bile anlaşılmadığımızı hissetmek çok zordur. Bu duygunu paylaştığın için teşekkür ederim.\n\n" +
                "Unutma ki okuldaki her bağ bir günde oluşmaz. Bugün ortak ilgi alanına sahip bir arkadaşına küçük bir gülümsemeyle 'Merhaba' demek yeni bir empati köprüsü kurabilir. Ben de buradayım."
            }
            lower.contains("öfke") || lower.contains("kızgın") -> {
                "Öfke de sevinç kadar insani bir duygu. Seni öfkelendiren olayı fark etmek bile büyük bir olgunluk.\n\n" +
                "Öfkenin sana zarar vermesine izin vermeden önce enerjini boşaltmak için derin bir nefes alabilir veya ellerini soğuk suyla yıkayabilirsin. Duygularını sakinlikle ifade etmek gücünü gösterir."
            }
            else -> {
                val modeNote = if (mode == AiMode.DEEP_THINKING) "Derin Empati Analizi" else "Empati Köprüsü"
                "Seni dinliyorum. Paylaştığın her duygu çok değerli. $modeNote olarak senin psikolojik iyi oluşunu desteklemek için buradayım.\n\n" +
                "Bu durumla ilgili biraz daha anlatmak ister misin, yoksa birlikte bir nefes ve rahatlama egzersizi mi yapalım? İhtiyaç duyarsan rehberlik öğretmenine de bildirim gönderebiliriz."
            }
        }
    }
}
