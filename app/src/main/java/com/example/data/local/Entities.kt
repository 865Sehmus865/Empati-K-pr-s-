package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val emotion: String,
    val intensity: Int, // 1 to 5
    val note: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isSynced: Boolean = true
)

@Entity(tableName = "guidance_requests")
data class GuidanceRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val requesterRole: String,
    val requesterName: String,
    val isAnonymous: Boolean,
    val topic: String,
    val message: String,
    val urgency: String,
    val status: String = "PENDING",
    val counselorReply: String? = null,
    val scheduledTime: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "incident_reports")
data class IncidentReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String,
    val location: String,
    val description: String,
    val isAnonymous: Boolean,
    val reporterName: String,
    val isUrgent: Boolean,
    val status: String = "İNCELEMEDE",
    val actionTaken: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "student_observations")
data class StudentObservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentCode: String,
    val studentName: String,
    val grade: String,
    val wellbeingRating: Int, // 1 to 5
    val peerRelationshipStatus: String, // "İyi", "Gözlemlenmeli", "Riskli"
    val observationNotes: String,
    val teacherName: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "system_notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val message: String,
    val category: String, // "REMINDER", "SUPPORT", "SYSTEM", "EXERCISE"
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
