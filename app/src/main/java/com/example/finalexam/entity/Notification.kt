package com.example.finalexam.entity

import com.google.firebase.Timestamp

data class Notification(
    val notificationId: String = "",
    val documentId: String = "",
    val senderId: String = "",
    val type: String = "",
    val title: String = "",
    val message: String = "",
    var isRead: Boolean = false,
    val createdAt: Timestamp = Timestamp.now()
)