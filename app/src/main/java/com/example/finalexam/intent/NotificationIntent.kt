package com.example.finalexam.intent

sealed class NotificationIntent {
    data object Loading : NotificationIntent()
    data object MarkAsReadAll : NotificationIntent()
    data object Initial : NotificationIntent()
    data object DeleteAll : NotificationIntent()
    data object Refresh : NotificationIntent()
    data class MarkAsRead(val notificationId: String) : NotificationIntent()
    data class Open(val notificationId: String) : NotificationIntent()
    data class Delete( val notificationId: String) : NotificationIntent()
}
