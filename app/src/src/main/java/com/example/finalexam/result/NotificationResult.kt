package com.example.finalexam.result

import com.example.finalexam.entity.Notification

sealed class NotificationResult {
    enum class ViewMode {
        PANEL,
        SCREEN,
        NONE
    }

    data object Load : NotificationResult()
    data object Close : NotificationResult()
    data class Show(val notifications: List<Notification>, val viewMode: ViewMode) :
        NotificationResult()

    data class Open(val documentId: String) : NotificationResult()
    data class Error(val message: String) : NotificationResult()
}