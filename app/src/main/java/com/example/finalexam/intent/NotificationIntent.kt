package com.example.finalexam.intent

sealed class NotificationIntent {
    data object Loading : NotificationIntent()
    data class MarkAsReadAll(val userId: String) : NotificationIntent()
    data class Initial(val userId: String) : NotificationIntent()
    data class DeleteAll(var userId: String) : NotificationIntent()
    data class Refresh(val userId: String) : NotificationIntent()
    data class MarkAsRead(val notificationId: String) : NotificationIntent()
    data class Open(val notificationId: String) : NotificationIntent()
    data class Delete(val notificationId: String) : NotificationIntent()
}
