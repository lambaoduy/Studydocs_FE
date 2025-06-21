//package com.example.finalexam.ui.components.notification
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.NotificationsNone
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import com.example.finalexam.ui.screens.NotificationColors
//
//@Composable
//fun EmptyNotificationState() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(32.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Icon(
//            imageVector = Icons.Outlined.NotificationsNone,
//            contentDescription = null,
//            modifier = Modifier.size(80.dp),
//            tint = NotificationColors.Outline
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Không có thông báo nào",
//            style = MaterialTheme.typography.titleMedium,
//            color = NotificationColors.OnSurfaceVariant,
//            fontWeight = FontWeight.Medium
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = "Các thông báo mới sẽ xuất hiện ở đây",
//            style = MaterialTheme.typography.bodyMedium,
//            color = NotificationColors.Outline
//        )
//    }
//}