package com.example.finalexam.reduce

import com.example.finalexam.result.NotificationResult
import com.example.finalexam.state.NotificationState

class NotificationReducer {
    fun reduce(state: NotificationState, result: NotificationResult): NotificationState =
        when (result) {
            is NotificationResult.Loading -> state.copy(isLoading = true)
            is NotificationResult.Delete -> state.copy(
                isLoading = false,
                notifications = state.notifications.filter { it.notificationId != result.notificationId },
                unreadCount = state.unreadCount - 1
            )

            is NotificationResult.DeleteAll -> state.copy(
                isLoading = false,
                notifications = emptyList(),
                unreadCount = 0
            )

            is NotificationResult.GetNotifications -> state.copy(
                isLoading = false,
                notifications = result.notifications,
                unreadCount = result.notifications.size
            )

            is NotificationResult.MarkAsRead -> state.copy(
                isLoading = false,
                notifications = state.notifications.map {
                    if (it.notificationId == result.notificationId) {
                        it.copy(isRead = true)
                    } else {
                        it
                    }
                })

            is NotificationResult.MarkAsReadAll -> state.copy(
                isLoading = false,
                notifications = state.notifications.map {
                    it.copy(isRead = true)
                })

            is NotificationResult.Error -> state.copy(
                isLoading = false,
                errorMessage = result.message
            )
        }

}