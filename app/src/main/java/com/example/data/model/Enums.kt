package com.example.data.model

enum class UserRole(val title: String, val description: String) {
    STUDENT("Öğrenci", "Duygu durumu, rahatlama, sınır çizme ve destek"),
    TEACHER("Öğretmen", "Sınıf gözlemleri, risk uyarıları ve empati etkinlikleri"),
    ADMIN("Yönetici", "Okul geneli refah raporları, risk haritası ve stratejiler"),
    COUNSELOR("Rehberlik (PDR)", "Canlı görüşmeler, randevular ve vaka takibi"),
    FAMILY("Aile / Veli", "Pozitif ebeveynlik, çocuk gelişimi ve okul iletişimi")
}

enum class EmotionType(val label: String, val emoji: String) {
    PEACEFUL("Huzurlu", "🌿"),
    HAPPY("Mutlu", "😊"),
    CALM("Sakin", "🍃"),
    CONFUSED("Kararsız", "🤔"),
    ANXIOUS("Kaygılı", "😟"),
    SAD("Üzgün", "😔"),
    ANGRY("Öfkeli", "😠"),
    LONELY("Yalnız", "🌧️")
}

enum class UrgencyLevel(val label: String) {
    NORMAL("Normal"),
    MEDIUM("Orta"),
    HIGH("Yüksek Öncelik"),
    CRITICAL("Acil Destek")
}

enum class RequestStatus(val label: String) {
    PENDING("Bekliyor"),
    SCHEDULED("Planlandı"),
    IN_PROGRESS("Görüşülüyor"),
    COMPLETED("Tamamlandı")
}

enum class BullyingType(val label: String) {
    VERBAL("Sözlü Zorbalık (Alay, Lakap)"),
    PHYSICAL("Fiziksel Zorbalık"),
    CYBER("Siber Zorbalık (Mesaj, Sosyal Medya)"),
    SOCIAL("Sosyal Dışlanma / Dedikodu"),
    PEER_PRESSURE("Akran Baskısı")
}
