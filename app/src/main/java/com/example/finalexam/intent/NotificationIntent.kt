package com.example.finalexam.intent

sealed class NotificationIntent {
    data object Load : NotificationIntent()
    data object ShowPanel : NotificationIntent()
    data object ShowScreen : NotificationIntent()
    data class Open(val notificationId: String) : NotificationIntent()
    data class Error(val message: String) : NotificationIntent()
}
