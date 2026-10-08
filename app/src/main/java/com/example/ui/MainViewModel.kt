package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.GuidanceRequestEntity
import com.example.data.local.IncidentReportEntity
import com.example.data.local.MoodEntity
import com.example.data.local.NotificationEntity
import com.example.data.local.StudentObservationEntity
import com.example.data.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppDestination(val label: String) {
    HOME("Ana Panel"),
    CHAT("AI Empati"),
    SCENARIOS("Sınırlarım"),
    SETTINGS("Ayarlar")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)

    // Current Active Role
    private val _currentRole = MutableStateFlow(UserRole.STUDENT)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    // Current Destination
    private val _currentDestination = MutableStateFlow(AppDestination.HOME)
    val currentDestination: StateFlow<AppDestination> = _currentDestination.asStateFlow()

    // Dark Mode Toggle
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // Low Power Mode Toggle
    private val _isLowPowerMode = MutableStateFlow(false)
    val isLowPowerMode: StateFlow<Boolean> = _isLowPowerMode.asStateFlow()

    // Room DB Flows
    val moodHistory: StateFlow<List<MoodEntity>> = db.moodDao().getAllMoods()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val guidanceRequests: StateFlow<List<GuidanceRequestEntity>> = db.guidanceDao().getAllRequests()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val incidentReports: StateFlow<List<IncidentReportEntity>> = db.incidentDao().getAllIncidents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val observations: StateFlow<List<StudentObservationEntity>> = db.observationDao().getAllObservations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationEntity>> = db.notificationDao().getAllNotifications()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotifCount: StateFlow<Int> = db.notificationDao().getUnreadCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun setRole(role: UserRole) {
        _currentRole.value = role
    }

    fun setDestination(dest: AppDestination) {
        _currentDestination.value = dest
    }

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun toggleLowPowerMode(enabled: Boolean) {
        _isLowPowerMode.value = enabled
    }

    fun saveMood(mood: MoodEntity) {
        viewModelScope.launch {
            db.moodDao().insertMood(mood)
            // Also add a supportive notification
            db.notificationDao().insertNotification(
                NotificationEntity(
                    title = "Duygu Günlüğün Güncellendi",
                    message = "Duygularını fark ettiğin ve kaydettiğin için tebrikler. Kendine şefkat göster.",
                    category = "SUPPORT"
                )
            )
        }
    }

    fun submitGuidanceRequest(request: GuidanceRequestEntity) {
        viewModelScope.launch {
            db.guidanceDao().insertRequest(request)
            db.notificationDao().insertNotification(
                NotificationEntity(
                    title = "Rehberlik Talebin Alındı",
                    message = "'${request.topic}' konulu talebin PDR servisine şifreli ve güvenli olarak iletildi.",
                    category = "SUPPORT"
                )
            )
        }
    }

    fun updateGuidanceRequest(request: GuidanceRequestEntity) {
        viewModelScope.launch {
            db.guidanceDao().updateRequest(request)
        }
    }

    fun submitIncident(report: IncidentReportEntity) {
        viewModelScope.launch {
            db.incidentDao().insertIncident(report)
            db.notificationDao().insertNotification(
                NotificationEntity(
                    title = "Olay Bildirimi Kaydedildi",
                    message = "Akran zorbalığı bildirimin anonim olarak okul yönetimi ve PDR masasına ulaştı.",
                    category = "SYSTEM"
                )
            )
        }
    }

    fun updateIncident(incident: IncidentReportEntity) {
        viewModelScope.launch {
            db.incidentDao().updateIncident(incident)
        }
    }

    fun saveObservation(observation: StudentObservationEntity) {
        viewModelScope.launch {
            db.observationDao().insertObservation(observation)
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            db.notificationDao().markAllAsRead()
        }
    }
}
