package com.example.data.model

data class ScenarioChoice(
    val id: Int,
    val text: String,
    val isEmpatheticAndSafe: Boolean,
    val feedback: String,
    val boundaryAdvice: String
)

data class BoundaryScenario(
    val id: String,
    val title: String,
    val category: String,
    val situation: String,
    val choices: List<ScenarioChoice>
)

object ScenariosRepository {
    val scenarios = listOf(
        BoundaryScenario(
            id = "s1",
            title = "Lakap Takma ve Alay Edilme",
            category = "Sözlü Zorbalık & Sınır Çizme",
            situation = "Koridorda bir grup öğrenci sana hoşlanmadığın bir lakapla seslenip gülüşüyor.",
            choices = listOf(
                Choice(
                    id = 1,
                    text = "Göz teması kurup sakin ve net bir sesle: 'Bu lakaptan hoşlanmıyorum, bana adımla seslenmenizi istiyorum' demek.",
                    isEmpatheticAndSafe = true,
                    feedback = "Harika! Sakin ve kararlı bir duruş sergileyerek sınırını net şekilde belirttin.",
                    boundaryAdvice = "Öfkelenmeden veya ağlamadan dik durmak, zorbanın beklediği tepkiyi boşa çıkarır."
                ),
                Choice(
                    id = 2,
                    text = "Onlara hakaret ederek aynı şekilde bağırmak.",
                    isEmpatheticAndSafe = false,
                    feedback = "Bu çatışmayı tırmandırabilir ve seni haksız duruma düşürebilir.",
                    boundaryAdvice = "Şiddete şiddetle karşılık vermek yerine sakinliğini koruyup kararlı sınır çizmek daha etkilidir."
                ),
                Choice(
                    id = 3,
                    text = "Hiçbir şey demeden içine atmak ve gün boyu üzülmek.",
                    isEmpatheticAndSafe = false,
                    feedback = "Duygularını içine atmak seni yıpratır ve davranışı onaylanmış gibi gösterir.",
                    boundaryAdvice = "Sınırını koruyamadığında okul rehberlik öğretmeninden yardım istemek en güçlü adımdır."
                )
            )
        ),
        BoundaryScenario(
            id = "s2",
            title = "Sınıf Grubunda Dışlanma ve İğneleyici Mesajlar",
            category = "Siber Zorbalık",
            situation = "Sınıfın ortak mesajlaşma grubunda biri senin hakkında alaycı bir şaka yaptı ve bazıları güldü.",
            choices = listOf(
                Choice(
                    id = 1,
                    text = "Mesajın ekran görüntüsünü alıp sakin kalmak ve 'Bu yaptığınız kırıcı ve doğru değil' yazıp rehberlik servisine bildirmek.",
                    isEmpatheticAndSafe = true,
                    feedback = "Mükemmel! Kanıtı saklamak ve soğukkanlılıkla sınır bildirmek siber zorbalıkta altın kuraldır.",
                    boundaryAdvice = "Dijital ayak izi kanıt oluşturur. Öfkeyle tartışmaya girmeden kanıtı yetişkinle paylaş."
                ),
                Choice(
                    id = 2,
                    text = "Grubu kapatıp tüm arkadaşlarına küsmek ve kimseyle konuşmamak.",
                    isEmpatheticAndSafe = false,
                    feedback = "İzolasyon seni daha çok yalnızlaştırır.",
                    boundaryAdvice = "Sana değer veren gerçek arkadaşlarınla bağını koparmadan sorun çıkaranla arana mesafe koy."
                ),
                Choice(
                    id = 3,
                    text = "Sen de onun hakkında gizli bir sırrı grupta yaymak.",
                    isEmpatheticAndSafe = false,
                    feedback = "Bu seni de bir siber zorbaya dönüştürür ve suçlu durumuna düşürür.",
                    boundaryAdvice = "Kötülüğü kötülükle yenemezsin; empati ve etik ilkeleri korumak seni güçlü kılar."
                )
            )
        ),
        BoundaryScenario(
            id = "s3",
            title = "Akran Baskısı ve 'Hayır' Diyebilme",
            category = "Akran Baskısı & Öz Saygı",
            situation = "Arkadaşların ders sırasında öğretmen görmeden komik bir şaka yapmanı ve kuralları çiğnemeni istiyor.",
            choices = listOf(
                Choice(
                    id = 1,
                    text = "'Hayır, bu dersi dinlemek istiyorum ve kuralları çiğnemek bana uymuyor' diyerek net durmak.",
                    isEmpatheticAndSafe = true,
                    feedback = "Tebrikler! Gerçek özgüven, başkalarının onayına bağımlı olmadan 'Hayır' diyebilmektir.",
                    boundaryAdvice = "Arkadaşların sana baskı yapsa bile ilkelerine sadık kalman onlarda bile gizli bir saygı uyandırır."
                ),
                Choice(
                    id = 2,
                    text = "Aralarında popüler olmak için istemeye istemeye şakayı yapmak.",
                    isEmpatheticAndSafe = false,
                    feedback = "Kendi sınırlarını çiğnedin. Bu suçluluk duygusu yaratır.",
                    boundaryAdvice = "Sevilmek için istemediğin şeyleri yapmak sınırlarını aşındırır."
                )
            )
        ),
        BoundaryScenario(
            id = "s4",
            title = "Bir Arkadaşına Yapılan Haksızlığa Şahit Olma",
            category = "Empati & Seyirci Kalmama",
            situation = "Sessiz bir sınıf arkadaşının çantasını sakladıklarını ve onunla dalga geçtiklerini gördün.",
            choices = listOf(
                Choice(
                    id = 1,
                    text = "Arkadaşının yanına gidip çantayı geri almasına destek olmak ve 'Bu yaptığınız hiç hoş değil' demek.",
                    isEmpatheticAndSafe = true,
                    feedback = "Harika bir empatik cesaret örneği! Seyirci kalmamak zorbalığı sonlandıran en büyük güçtür.",
                    boundaryAdvice = "Zorbalık seyirci bulamadığında söner. Bir kişinin yanında durmak ona dünyaları bağışlar."
                ),
                Choice(
                    id = 2,
                    text = "Gülümseyip geçmek ve 'bana dokunmayan yılan bin yaşasın' diye düşünmek.",
                    isEmpatheticAndSafe = false,
                    feedback = "Sessiz kalmak zorbalara onay vermek anlamına gelir.",
                    boundaryAdvice = "Bugün sessiz kalınan zorbalık yarın herkese ulaşabilir. Güvenli şekilde nöbetçi öğretmene haber ver."
                )
            )
        )
    )

    private fun Choice(id: Int, text: String, isEmpatheticAndSafe: Boolean, feedback: String, boundaryAdvice: String) =
        ScenarioChoice(id, text, isEmpatheticAndSafe, feedback, boundaryAdvice)
}
