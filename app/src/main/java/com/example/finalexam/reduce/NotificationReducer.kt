package com.example.finalexam.reduce

import com.example.finalexam.result.NotificationResult
import com.example.finalexam.result.NotificationResult.ViewMode
import com.example.finalexam.state.NotificationState

class NotificationReducer {
    fun reduce(state: NotificationState, result: NotificationResult): NotificationState =
        when (result) {
            is NotificationResult.Load -> state.copy(isLoading = true)
            is NotificationResult.Close -> state.copy(viewMode = ViewMode.NONE)
            is NotificationResult.Open -> state.copy(
                documentId = result.documentId,
                viewMode = ViewMode.NONE
            )

            is NotificationResult.Show -> state.copy(
                notifications = result.notifications,
                viewMode = result.viewMode,
                isLoading = false,
                unreadCount = 0
            )

            is NotificationResult.Error -> state.copy(
                errorMessage = result.message,
                isLoading = false
            )
        }

}