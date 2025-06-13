package com.example.finalexam.entity

import com.example.finalexam.data.enums.NotificationType
import com.google.firebase.Timestamp

data class Notification(
    val notificationId: String,
    val senderId: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val targetId: String,
    val isRead: Boolean,
    val createdAt: Timestamp
)

