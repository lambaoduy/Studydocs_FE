package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationMarkAsReadAllUseCase

class NotificationMarkAsReadAllHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationMarkAsReadAllUseCase = NotificationMarkAsReadAllUseCase()
    
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.MarkAsReadAll

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        notificationMarkAsReadAllUseCase.invoke()
            .onSuccess { success ->
                if (success) {
                    setResult(NotificationResult.MarkAsReadAll)
                } else {
                    setResult(NotificationResult.Error("Failed to mark all notifications as read."))
                }
            }
            .onFailure { error ->
                setResult(NotificationResult.Error(error.message ?: "An unknown error occurred."))
            }
    }
}