package com.example.finalexam.state

import com.example.finalexam.entity.Notification

data class NotificationState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val unreadCount: Int = 0,
    val errorMessage: String? = null,
)

