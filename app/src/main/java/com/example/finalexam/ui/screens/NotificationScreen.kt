package com.example.finalexam.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.data.enums.NotificationType
import com.example.finalexam.entity.Notification
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.ui.components.common.EmptyStates
import com.example.finalexam.ui.components.common.ErrorState
import com.example.finalexam.ui.components.common.LoadingState
import com.example.finalexam.ui.components.notification.NotificationList
import com.example.finalexam.ui.components.notification.NotificationTopBar
import com.example.finalexam.viewmodel.NotificationViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Notification Color Scheme
object NotificationColors {
    val Primary = Color(0xFF6B46C1) // Purple
    val PrimaryLight = Color(0xFFEDE9FE)
    val Error = Color(0xFFDC2626) // Red
    val Info = Color(0xFF2563EB) // Blue
    val Surface = Color(0xFFFFFFFF)
    val Background = Color(0xFFF8FAFC)
    val OnSurface = Color(0xFF1E293B)
    val OnSurfaceVariant = Color(0xFF64748B)
    val Outline = Color(0xFFCBD5E1)
}

enum class NotificationTypeUI(
    val icon: ImageVector,
    val color: Color
) {
    LIKE(Icons.Filled.Favorite, NotificationColors.Error),
    UPLOAD(Icons.Outlined.CloudUpload, NotificationColors.Primary),
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = viewModel(),
    navigateToFollow: () -> Unit,
    onBackClick: () -> Unit = {},
    onNotificationClick: (Notification) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.processIntent(NotificationIntent.Initial)
        }
    }

    Scaffold(
        containerColor = NotificationColors.Background,
        topBar = {
            NotificationTopBar(
                unreadCount = state.unreadCount,
                hasNotification = !state.notifications.isEmpty(),
                onBackClick = onBackClick,
                onMarkAllRead = {
                    scope.launch {
                        viewModel.processIntent(NotificationIntent.MarkAsReadAll)
                    }
                },
                onDeleteAll = {
                    scope.launch {
                        viewModel.processIntent(NotificationIntent.DeleteAll)
                    }
                },
                onOpenNotificationSettings = navigateToFollow
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    LoadingState()
                }

                state.errorMessage != null -> {
                    ErrorState(
                        message = state.errorMessage!!,
                        onRetry = {
                            scope.launch {
                                viewModel.processIntent(NotificationIntent.Refresh)
                            }
                        }
                    )
                }

                state.notifications.isEmpty() -> {
                    EmptyStates.NoNotifications()
                }

                else -> {
                    NotificationList(
                        notifications = state.notifications,
                        onNotificationClick = { notification ->
                            scope.launch {
                                viewModel.processIntent(
                                    NotificationIntent.MarkAsRead(
                                        notificationId = notification.notificationId
                                    )
                                )
                            }
                            onNotificationClick(notification)
                        },
                        onDeleteClick = { notificationId ->
                            scope.launch {
                                viewModel.processIntent(
                                    NotificationIntent.Delete(
                                        notificationId = notificationId
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


fun getNotificationTypeUI(type: NotificationType): NotificationTypeUI {
    return when (type) {
        NotificationType.POST_LIKED -> NotificationTypeUI.LIKE
        NotificationType.NEW_POST -> NotificationTypeUI.UPLOAD
    }
}


fun Date.getRelativeTime(): String {
    val now = System.currentTimeMillis()
    val diff = now - this.time

    return when {
        diff < 60000 -> "Vừa xong"
        diff < 3600000 -> "${diff / 60000} phút trước"
        diff < 86400000 -> "${diff / 3600000} giờ trước"
        diff < 604800000 -> "${diff / 86400000} ngày trước"
        else -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)
    }
}