package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.UserRole
import com.example.ui.AppDestination
import com.example.ui.MainViewModel
import com.example.ui.components.IncidentReportDialog
import com.example.ui.components.NotificationCenterDialog
import com.example.ui.components.QuickHelpModal
import com.example.ui.components.RoleSwitcherModal
import com.example.ui.screens.admin.AdminScreen
import com.example.ui.screens.chat.AiChatScreen
import com.example.ui.screens.counselor.CounselorScreen
import com.example.ui.screens.family.FamilyScreen
import com.example.ui.screens.scenarios.ScenariosScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.student.StudentScreen
import com.example.ui.screens.teacher.TeacherScreen
import com.example.ui.theme.EmpatiKoprusuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmpatiKoprusuApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpatiKoprusuApp(viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

    val currentRole by viewModel.currentRole.collectAsState()
    val currentDestination by viewModel.currentDestination.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isLowPowerMode by viewModel.isLowPowerMode.collectAsState()

    val moodHistory by viewModel.moodHistory.collectAsState()
    val guidanceRequests by viewModel.guidanceRequests.collectAsState()
    val incidentReports by viewModel.incidentReports.collectAsState()
    val observations by viewModel.observations.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val unreadCount by viewModel.unreadNotifCount.collectAsState()

    var showRoleModal by remember { mutableStateOf(false) }
    var showNotifDialog by remember { mutableStateOf(false) }
    var showIncidentDialog by remember { mutableStateOf(false) }
    var showDirectHelpModal by remember { mutableStateOf(false) }

    // Intercept Back button if in secondary screens
    BackHandler(enabled = currentDestination != AppDestination.HOME) {
        viewModel.setDestination(AppDestination.HOME)
    }

    if (showRoleModal) {
        RoleSwitcherModal(
            currentRole = currentRole,
            onRoleSelected = { viewModel.setRole(it) },
            onDismiss = { showRoleModal = false }
        )
    }

    if (showNotifDialog) {
        NotificationCenterDialog(
            notifications = notifications,
            onDismiss = { showNotifDialog = false },
            onMarkAllRead = { viewModel.markAllNotificationsRead() }
        )
    }

    if (showIncidentDialog) {
        IncidentReportDialog(
            onDismiss = { showIncidentDialog = false },
            onSubmit = { report ->
                viewModel.submitIncident(report)
                showIncidentDialog = false
                Toast.makeText(context, "Akran zorbalığı bildirimi güvenle kaydedildi.", Toast.LENGTH_SHORT).show()
            }
        )
    }

    if (showDirectHelpModal) {
        QuickHelpModal(
            requesterRole = currentRole.title,
            onDismiss = { showDirectHelpModal = false },
            onSubmit = { req ->
                viewModel.submitGuidanceRequest(req)
                showDirectHelpModal = false
                Toast.makeText(context, "PDR rehberlik talebiniz iletildi.", Toast.LENGTH_SHORT).show()
            }
        )
    }

    EmpatiKoprusuTheme(darkTheme = isDarkMode) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("🌿", fontSize = 20.sp)
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Empati Köprüsü",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                // Role switcher pill in title
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier
                                        .clickable { showRoleModal = true }
                                        .testTag("role_switcher_pill")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = currentRole.title,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        // Quick Bullying Incident Report Button
                        IconButton(
                            onClick = { showIncidentDialog = true },
                            modifier = Modifier.testTag("report_incident_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Zorbalık Bildir",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    actions = {
                        // Notifications Center Button with Badge
                        IconButton(
                            onClick = { showNotifDialog = true },
                            modifier = Modifier.testTag("notification_center_button")
                        ) {
                            BadgedBox(
                                badge = {
                                    if (unreadCount > 0) {
                                        Badge(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        ) {
                                            Text("$unreadCount")
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Bildirimler",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    NavigationBarItem(
                        selected = currentDestination == AppDestination.HOME,
                        onClick = { viewModel.setDestination(AppDestination.HOME) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Dashboard,
                                contentDescription = "Ana Panel"
                            )
                        },
                        label = { Text("Panel") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_home")
                    )

                    NavigationBarItem(
                        selected = currentDestination == AppDestination.CHAT,
                        onClick = { viewModel.setDestination(AppDestination.CHAT) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = "AI Empati Sohbeti"
                            )
                        },
                        label = { Text("AI Sohbet") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_chat")
                    )

                    NavigationBarItem(
                        selected = currentDestination == AppDestination.SCENARIOS,
                        onClick = { viewModel.setDestination(AppDestination.SCENARIOS) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = "Sınırlarımı Çiziyorum"
                            )
                        },
                        label = { Text("Sınırlarım") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_scenarios")
                    )

                    NavigationBarItem(
                        selected = currentDestination == AppDestination.SETTINGS,
                        onClick = { viewModel.setDestination(AppDestination.SETTINGS) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Ayarlar"
                            )
                        },
                        label = { Text("Ayarlar") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_settings")
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Crossfade(
                    targetState = currentDestination,
                    label = "screenCrossfade"
                ) { dest ->
                    when (dest) {
                        AppDestination.HOME -> {
                            when (currentRole) {
                                UserRole.STUDENT -> StudentScreen(
                                    moodHistory = moodHistory,
                                    guidanceRequests = guidanceRequests,
                                    onSaveMood = { viewModel.saveMood(it) },
                                    onSubmitGuidanceRequest = { viewModel.submitGuidanceRequest(it) },
                                    onNavigateToChat = { viewModel.setDestination(AppDestination.CHAT) },
                                    onNavigateToScenarios = { viewModel.setDestination(AppDestination.SCENARIOS) }
                                )

                                UserRole.TEACHER -> TeacherScreen(
                                    observations = observations,
                                    onSaveObservation = { viewModel.saveObservation(it) },
                                    onRequestGuidance = { showDirectHelpModal = true }
                                )

                                UserRole.ADMIN -> AdminScreen(
                                    moods = moodHistory,
                                    requests = guidanceRequests,
                                    incidents = incidentReports,
                                    observations = observations,
                                    onExportReport = {
                                        Toast.makeText(context, "Anonim Okul Psikolojik Raporu başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show()
                                    }
                                )

                                UserRole.COUNSELOR -> CounselorScreen(
                                    requests = guidanceRequests,
                                    incidents = incidentReports,
                                    onUpdateRequest = { viewModel.updateGuidanceRequest(it) },
                                    onUpdateIncident = { viewModel.updateIncident(it) }
                                )

                                UserRole.FAMILY -> FamilyScreen(
                                    onRequestGuidance = { showDirectHelpModal = true }
                                )
                            }
                        }

                        AppDestination.CHAT -> {
                            AiChatScreen()
                        }

                        AppDestination.SCENARIOS -> {
                            ScenariosScreen(
                                onBack = { viewModel.setDestination(AppDestination.HOME) },
                                onNavigateToChat = { viewModel.setDestination(AppDestination.CHAT) }
                            )
                        }

                        AppDestination.SETTINGS -> {
                            SettingsScreen(
                                isDarkMode = isDarkMode,
                                onToggleDarkMode = { viewModel.toggleDarkMode(it) },
                                isLowPowerMode = isLowPowerMode,
                                onToggleLowPowerMode = { viewModel.toggleLowPowerMode(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}
