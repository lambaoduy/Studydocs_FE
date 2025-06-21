//package com.example.finalexam.ui.components.notification
//
//import androidx.compose.animation.core.Spring
//import androidx.compose.animation.core.spring
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.finalexam.entity.Notification
//import com.example.finalexam.ui.screens.NotificationColors
//
//@Composable
//fun NotificationList(
//    notifications: List<Notification>,
//    onNotificationClick: (Notification) -> Unit,
//    onDeleteClick: (String) -> Unit
//) {
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(1.dp)
//    ) {
//        itemsIndexed(
//            items = notifications,
//            key = { _, it -> it.notificationId }
//        ) { index, notification ->
//            NotificationItem(
//                notification = notification,
//                onClick = { onNotificationClick(notification) },
//                onDeleteClick = { onDeleteClick(notification.notificationId) },
//                modifier = Modifier.animateItem(
//                    placementSpec = spring(
//                        dampingRatio = Spring.DampingRatioMediumBouncy,
//                        stiffness = Spring.StiffnessLow
//                    )
//                ),
//            )
//            if (index < notifications.lastIndex) {
//                HorizontalDivider(
//                    thickness = 0.5.dp,
//                    color = NotificationColors.Outline
//                )
//            }
//        }
//    }
//}