package com.example.finalexam.intent

sealed class NotificationIntent {
    data object Close: NotificationIntent()
    data class ShowPanel(val userid: String) : NotificationIntent()
    data class ShowScreen(val userid: String)  : NotificationIntent()
    data class Open(val notificationId: String) : NotificationIntent()
}
