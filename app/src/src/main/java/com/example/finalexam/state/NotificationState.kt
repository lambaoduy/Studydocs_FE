package com.example.finalexam.state

import com.example.finalexam.entity.Notification
import com.example.finalexam.result.NotificationResult.ViewMode

data class NotificationState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val documentId: String = "",
    val unreadCount: Int = 0,
    val errorMessage: String? = null,
    val viewMode: ViewMode = ViewMode.NONE

)

