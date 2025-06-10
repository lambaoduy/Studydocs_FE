package com.example.finalexam.result

import com.example.finalexam.entity.Notification

sealed class NotificationResult {
    data object Loading : NotificationResult()
    data object DeleteAll : NotificationResult()
    data object MarkAsReadAll : NotificationResult()

    data class GetNotifications(val notifications: List<Notification>) : NotificationResult()
    data class Error(val message: String) : NotificationResult()
    data class Delete(val notificationId: String) : NotificationResult()
    data class MarkAsRead(val notificationId: String) : NotificationResult()
}