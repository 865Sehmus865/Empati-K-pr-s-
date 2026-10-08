package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        MoodEntity::class,
        GuidanceRequestEntity::class,
        IncidentReportEntity::class,
        StudentObservationEntity::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    abstract fun guidanceDao(): GuidanceDao
    abstract fun incidentDao(): IncidentDao
    abstract fun observationDao(): ObservationDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "empati_koprusu_db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            INSTANCE?.let { database ->
                                populateInitialData(database)
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateInitialData(db: AppDatabase) {
            val now = System.currentTimeMillis()

            // Pre-seed sample moods
            db.moodDao().insertMood(
                MoodEntity(
                    emotion = "PEACEFUL",
                    intensity = 4,
                    note = "Bugün arkadaşlarımla güzel bir grup projesi yaptık, huzurluyum.",
                    timestamp = now - 86400000L * 2
                )
            )
            db.moodDao().insertMood(
                MoodEntity(
                    emotion = "ANXIOUS",
                    intensity = 3,
                    note = "Matematik sınavı öncesi biraz tedirgin hissettim ama nefes egzersizi iyi geldi.",
                    timestamp = now - 86400000L
                )
            )
            db.moodDao().insertMood(
                MoodEntity(
                    emotion = "CALM",
                    intensity = 5,
                    note = "Sınırlarımı çizdim ve 'hayır' demeyi başardım, kendimle gurur duyuyorum.",
                    timestamp = now - 3600000L * 3
                )
            )

            // Pre-seed guidance requests
            db.guidanceDao().insertRequest(
                GuidanceRequestEntity(
                    requesterRole = "Öğrenci",
                    requesterName = "Anonim Öğrenci (8-B)",
                    isAnonymous = true,
                    topic = "Akran Dışlanması & Yalnızlık",
                    message = "Teneffüslerde bazı arkadaşlarım oyunlara beni almıyor, konuşmak istiyorum.",
                    urgency = "HIGH",
                    status = "SCHEDULED",
                    counselorReply = "Yarın 3. teneffüste rehberlik odasında seni bekliyorum, her zaman yanındayız.",
                    scheduledTime = "Yarın 11:20",
                    timestamp = now - 3600000L * 5
                )
            )
            db.guidanceDao().insertRequest(
                GuidanceRequestEntity(
                    requesterRole = "Aile / Veli",
                    requesterName = "Fatma K. (Veli)",
                    isAnonymous = false,
                    topic = "Sınav Kaygısı ve Ev İletişimi",
                    message = "Oğlum sınav öncesi çok gergin oluyor, evde nasıl yaklaşmamız gerektiğini danışmak istiyoruz.",
                    urgency = "NORMAL",
                    status = "PENDING",
                    timestamp = now - 86400000L
                )
            )

            // Pre-seed incident reports for admin / counselor analytics
            db.incidentDao().insertIncident(
                IncidentReportEntity(
                    category = "Sözlü Zorbalık (Alay, Lakap)",
                    location = "Okul Bahçesi - Kantin Yanı",
                    description = "Bazı öğrenciler bir arkadaşımızın boyu ve gözlüğüyle sürekli alay ediyordu.",
                    isAnonymous = true,
                    reporterName = "Gizli Bildirim",
                    isUrgent = false,
                    status = "İNCELEMEDE",
                    timestamp = now - 86400000L * 3
                )
            )
            db.incidentDao().insertIncident(
                IncidentReportEntity(
                    category = "Siber Zorbalık (Mesaj, Sosyal Medya)",
                    location = "Sınıf WhatsApp Grubu",
                    description = "Grupta bir arkadaşımızın izinsiz fotoğrafı paylaşılarak rencide edici yorumlar yapıldı.",
                    isAnonymous = false,
                    reporterName = "Mert Y. (7-A)",
                    isUrgent = true,
                    status = "İŞLEM YAPILDI",
                    actionTaken = "Grup yöneticileri ve velilerle PDR servisi koordineli görüştü.",
                    timestamp = now - 86400000L * 4
                )
            )

            // Pre-seed teacher student observations
            db.observationDao().insertObservation(
                StudentObservationEntity(
                    studentCode = "ÖĞR-204",
                    studentName = "Zeynep A.",
                    grade = "7-B",
                    wellbeingRating = 4,
                    peerRelationshipStatus = "İyi",
                    observationNotes = "Empati oyunlarında çok yapıcı, arkadaşlarına destek oluyor.",
                    teacherName = "Ahmet Öğretmen (Türkçe)",
                    timestamp = now - 86400000L * 2
                )
            )
            db.observationDao().insertObservation(
                StudentObservationEntity(
                    studentCode = "ÖĞR-188",
                    studentName = "Caner D.",
                    grade = "8-A",
                    wellbeingRating = 2,
                    peerRelationshipStatus = "Gözlemlenmeli",
                    observationNotes = "Son iki gündür içe kapanık ve teneffüslerde yalnız oturuyor. PDR servisine bildirildi.",
                    teacherName = "Merve Öğretmen (Rehberlik)",
                    timestamp = now - 86400000L
                )
            )

            // Pre-seed system notifications
            db.notificationDao().insertNotification(
                NotificationEntity(
                    title = "Günün İyilik & Empati Hatırlatıcısı",
                    message = "Bugün bir arkadaşına içtenlikle 'Günün nasıl geçti?' diye sormayı deneyebilirsin.",
                    category = "REMINDER",
                    isRead = false,
                    timestamp = now - 3600000L
                )
            )
            db.notificationDao().insertNotification(
                NotificationEntity(
                    title = "Haftalık Rahatlama Seansı",
                    message = "Zihnini dinlendirmek için 3 dakikalık '4-7-8 Nefes Egzersizi' hazır.",
                    category = "EXERCISE",
                    isRead = false,
                    timestamp = now - 7200000L
                )
            )
            db.notificationDao().insertNotification(
                NotificationEntity(
                    title = "Rehberlik Servisi Duyurusu",
                    message = "Akran arabuluculuğu atölyemiz Çarşamba günü kütüphanede toplanıyor.",
                    category = "SYSTEM",
                    isRead = true,
                    timestamp = now - 86400000L
                )
            )
        }
    }
}
