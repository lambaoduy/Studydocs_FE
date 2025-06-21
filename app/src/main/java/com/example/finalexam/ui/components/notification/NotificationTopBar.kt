//package com.example.finalexam.ui.components.notification
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.MoreVert
//import androidx.compose.material.icons.outlined.DeleteSweep
//import androidx.compose.material.icons.outlined.DoneAll
//import androidx.compose.material.icons.outlined.Settings
//import androidx.compose.material3.Badge
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.finalexam.ui.screens.NotificationColors
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NotificationTopBar(
//    unreadCount: Int,
//    hasNotification: Boolean,
//    onBackClick: () -> Unit,
//    onMarkAllRead: () -> Unit,
//    onDeleteAll: () -> Unit,
//    onOpenNotificationSettings: () -> Unit
//) {
//    var showMenu by remember { mutableStateOf(false) }
//
//    TopAppBar(
//        title = {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Thông báo",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = NotificationColors.OnSurface
//                )
//                if (unreadCount > 0) {
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Badge(
//                        containerColor = NotificationColors.Primary,
//                        modifier = Modifier.animateContentSize()
//                    ) {
//                        Text(
//                            text = if (unreadCount > 99) "99+" else unreadCount.toString(),
//                            fontSize = 11.sp,
//                            color = Color.White
//                        )
//                    }
//                }
//            }
//        },
//        navigationIcon = {
//            IconButton(onClick = onBackClick) {
//                Icon(
//                    Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Quay lại",
//                    tint = NotificationColors.OnSurface
//                )
//            }
//        },
//        actions = {
//            if (unreadCount > 0) {
//                TextButton(
//                    onClick = onMarkAllRead,
//                    colors = ButtonDefaults.textButtonColors(
//                        contentColor = NotificationColors.Primary
//                    )
//                ) {
//                    Text(
//                        text = "Đánh dấu tất cả",
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//            }
//            IconButton(onClick = { showMenu = true }) {
//                Icon(
//                    Icons.Default.MoreVert,
//                    contentDescription = "Tùy chọn",
//                    tint = NotificationColors.OnSurface
//                )
//            }
//            DropdownMenu(
//                expanded = showMenu,
//                onDismissRequest = { showMenu = false }
//            ) {
//                DropdownMenuItem(
//                    text = { Text("Đánh dấu tất cả đã đọc") },
//                    onClick = {
//                        if (unreadCount > 0) {
//                            onMarkAllRead()
//                        }
//                        showMenu = false
//                    },
//                    leadingIcon = {
//                        Icon(Icons.Outlined.DoneAll, contentDescription = null)
//                    },
//                    enabled = unreadCount > 0
//                )
//
//                DropdownMenuItem(
//                    text = { Text("Xóa tất cả") },
//                    onClick = {
//                        if (hasNotification) {
//                            onDeleteAll()
//                        }
//                        showMenu = false
//                    },
//                    leadingIcon = {
//                        Icon(Icons.Outlined.DeleteSweep, contentDescription = null)
//                    },
//                    enabled = hasNotification
//                )
//                HorizontalDivider()
//                DropdownMenuItem(
//                    text = { Text("Cài đặt thông báo") },
//                    onClick = {
//                        onOpenNotificationSettings()
//                        showMenu = false
//                    },
//                    leadingIcon = {
//                        Icon(Icons.Outlined.Settings, contentDescription = null)
//                    }
//                )
//            }
//        },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = NotificationColors.Surface
//        )
//    )
//}