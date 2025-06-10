package com.example.finalexam.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.GetApp
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SystemUpdate
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.Notification
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.viewmodel.NotificationViewModel
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Notification Color Scheme
object NotificationColors {
    val Primary = Color(0xFF6B46C1) // Purple
    val PrimaryLight = Color(0xFFEDE9FE)
    val Secondary = Color(0xFF059669) // Green
    val Warning = Color(0xFFF59E0B) // Amber
    val Error = Color(0xFFDC2626) // Red
    val Info = Color(0xFF2563EB) // Blue
    val Surface = Color(0xFFFFFFFF)
    val Background = Color(0xFFF8FAFC)
    val OnSurface = Color(0xFF1E293B)
    val OnSurfaceVariant = Color(0xFF64748B)
    val Outline = Color(0xFFCBD5E1)
}

// Notification Type Enum for UI
enum class NotificationType(val icon: ImageVector, val color: Color) {
    DOCUMENT_SHARED(Icons.Outlined.Description, NotificationColors.Info),
    DOCUMENT_LIKED(Icons.Filled.Favorite, NotificationColors.Error),
    DOCUMENT_COMMENTED(Icons.Outlined.ChatBubbleOutline, NotificationColors.Secondary),
    DOCUMENT_DOWNLOADED(Icons.Outlined.GetApp, NotificationColors.Primary),
    FOLLOW_REQUEST(Icons.Outlined.PersonAdd, NotificationColors.Warning),
    SYSTEM_UPDATE(Icons.Outlined.SystemUpdate, NotificationColors.Info),
    ACHIEVEMENT(Icons.Filled.EmojiEvents, NotificationColors.Warning),
    DEFAULT(Icons.Outlined.Notifications, NotificationColors.OnSurfaceVariant)
}

val notifications = listOf(
    Notification(
        notificationId = "1",
        title = "Tài liệu được chia sẻ",
        message = "Bạn vừa nhận được một tài liệu mới.",
        createdAt = Timestamp.now(),
        isRead = false,
        type = "DOCUMENT_SHARED"
    ),
    Notification(
        notificationId = "2",
        title = "Có người thích tài liệu của bạn",
        message = "Người dùng A vừa thích tài liệu của bạn.",
        createdAt = Timestamp.now(),
        isRead = true,
        type = "DOCUMENT_LIKED"
    )
)
@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationList(notifications = notifications, onNotificationClick = {}, onDeleteClick = {})
}

val userId = ""
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onNotificationClick: (Notification) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.processIntent(NotificationIntent.Initial(userId))
        }
    }

    Scaffold(
        containerColor = NotificationColors.Background,
        topBar = {
            NotificationTopBar(
                unreadCount = state.unreadCount,
                onBackClick = onBackClick,
                onMarkAllRead = {
                    scope.launch {
                        viewModel.processIntent(NotificationIntent.MarkAsReadAll(userId))
                    }
                },
                onDeleteAll = {
                    scope.launch {
                        viewModel.processIntent(NotificationIntent.DeleteAll(userId))
                    }
                }
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
                                viewModel.processIntent(NotificationIntent.Refresh(userId))
                            }
                        }
                    )
                }
                state.notifications.isEmpty() -> {
                    EmptyNotificationState()
                }
                else -> {
                    NotificationList(
                        notifications = state.notifications,
                        onNotificationClick = { notification ->
                            scope.launch {
                                viewModel.processIntent(
                                    NotificationIntent.MarkAsRead(notification.notificationId)
                                )
                            }
                            onNotificationClick(notification)
                        },
                        onDeleteClick = { notificationId ->
                            scope.launch {
                                viewModel.processIntent(
                                    NotificationIntent.Delete(notificationId)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTopBar(
    unreadCount: Int,
    onBackClick: () -> Unit,
    onMarkAllRead: () -> Unit,
    onDeleteAll: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Thông báo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = NotificationColors.OnSurface
                )
                if (unreadCount > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Badge(
                        containerColor = NotificationColors.Primary,
                        modifier = Modifier.animateContentSize()
                    ) {
                        Text(
                            text = if (unreadCount > 99) "99+" else unreadCount.toString(),
                            fontSize = 11.sp,
                            color = Color.White
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = NotificationColors.OnSurface
                )
            }
        },
        actions = {
            if (unreadCount > 0) {
                TextButton(
                    onClick = onMarkAllRead,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = NotificationColors.Primary
                    )
                ) {
                    Text(
                        text = "Đánh dấu tất cả",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            IconButton(onClick = { showMenu = true }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Tùy chọn",
                    tint = NotificationColors.OnSurface
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Đánh dấu tất cả đã đọc") },
                    onClick = {
                        onMarkAllRead()
                        showMenu = false
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.DoneAll, contentDescription = null)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Xóa tất cả") },
                    onClick = {
                        onDeleteAll()
                        showMenu = false
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.DeleteSweep, contentDescription = null)
                    }
                )
                Divider()
                DropdownMenuItem(
                    text = { Text("Cài đặt thông báo") },
                    onClick = { showMenu = false },
                    leadingIcon = {
                        Icon(Icons.Outlined.Settings, contentDescription = null)
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = NotificationColors.Surface
        )
    )
}

@Composable
fun NotificationList(
    notifications: List<Notification>,
    onNotificationClick: (Notification) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        itemsIndexed(
            items = notifications,
            key = { _, it -> it.notificationId }
        ) { index,notification ->
            NotificationNotificationItem(
                notification = notification,
                onClick = { onNotificationClick(notification) },
                onDeleteClick = { onDeleteClick(notification.notificationId) },
//                modifier = Modifier.animateItemPlacement(
//                    animationSpec = spring(
//                        dampingRatio = Spring.DampingRatioMediumBouncy,
//                        stiffness = Spring.StiffnessLow
//                    )
//                ),
            )
            if (index < notifications.lastIndex) {
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = NotificationColors.Outline
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationNotificationItem(
    notification: Notification,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val notificationType = getNotificationType(notification.type)
    val alpha by animateFloatAsState(
        targetValue = if (notification.isRead) 0.8f else 1f,
        animationSpec = tween(300),
        label = "notification_alpha"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead)
                NotificationColors.Surface else NotificationColors.PrimaryLight.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = notificationType.color.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notificationType.icon,
                    contentDescription = null,
                    tint = notificationType.color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.SemiBold,
                        color = NotificationColors.OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    // Unread indicator
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = NotificationColors.Primary,
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = NotificationColors.OnSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.createdAt.toDate().getRelativeTime(),
                        style = MaterialTheme.typography.labelSmall,
                        color = NotificationColors.Outline
                    )

                    // Action buttons
                    Row {
                        if (notification.type == "FOLLOW_REQUEST" && !notification.isRead) {
                            TextButton(
                                onClick = { /* Handle accept */ },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = NotificationColors.Primary
                                ),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("Chấp nhận", fontSize = 12.sp)
                            }
                        }

                        IconButton(
                            onClick = onDeleteClick,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "Xóa",
                                tint = NotificationColors.Outline,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyNotificationState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.NotificationsNone,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = NotificationColors.Outline
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Không có thông báo nào",
            style = MaterialTheme.typography.titleMedium,
            color = NotificationColors.OnSurfaceVariant,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Các thông báo mới sẽ xuất hiện ở đây",
            style = MaterialTheme.typography.bodyMedium,
            color = NotificationColors.Outline
        )
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = NotificationColors.Primary,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = NotificationColors.Error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Có lỗi xảy ra",
            style = MaterialTheme.typography.titleMedium,
            color = NotificationColors.OnSurface,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = NotificationColors.OnSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = NotificationColors.Primary
            )
        ) {
            Icon(
                Icons.Outlined.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Thử lại")
        }
    }
}

// Helper Functions
fun getNotificationType(type: String): NotificationType {
    return when (type.uppercase()) {
        "DOCUMENT_SHARED" -> NotificationType.DOCUMENT_SHARED
        "DOCUMENT_LIKED" -> NotificationType.DOCUMENT_LIKED
        "DOCUMENT_COMMENTED" -> NotificationType.DOCUMENT_COMMENTED
        "DOCUMENT_DOWNLOADED" -> NotificationType.DOCUMENT_DOWNLOADED
        "FOLLOW_REQUEST" -> NotificationType.FOLLOW_REQUEST
        "SYSTEM_UPDATE" -> NotificationType.SYSTEM_UPDATE
        "ACHIEVEMENT" -> NotificationType.ACHIEVEMENT
        else -> NotificationType.DEFAULT
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