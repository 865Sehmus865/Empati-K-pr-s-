package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC")
    fun getAllMoods(): Flow<List<MoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: MoodEntity): Long

    @Query("DELETE FROM mood_entries WHERE id = :id")
    suspend fun deleteMood(id: Long)
}

@Dao
interface GuidanceDao {
    @Query("SELECT * FROM guidance_requests ORDER BY timestamp DESC")
    fun getAllRequests(): Flow<List<GuidanceRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: GuidanceRequestEntity): Long

    @Update
    suspend fun updateRequest(request: GuidanceRequestEntity)

    @Query("SELECT * FROM guidance_requests WHERE id = :id")
    suspend fun getRequestById(id: Long): GuidanceRequestEntity?
}

@Dao
interface IncidentDao {
    @Query("SELECT * FROM incident_reports ORDER BY timestamp DESC")
    fun getAllIncidents(): Flow<List<IncidentReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncident(incident: IncidentReportEntity): Long

    @Update
    suspend fun updateIncident(incident: IncidentReportEntity)
}

@Dao
interface ObservationDao {
    @Query("SELECT * FROM student_observations ORDER BY timestamp DESC")
    fun getAllObservations(): Flow<List<StudentObservationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObservation(observation: StudentObservationEntity): Long
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM system_notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM system_notifications WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity): Long

    @Query("UPDATE system_notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("UPDATE system_notifications SET isRead = 1")
    suspend fun markAllAsRead()
}
