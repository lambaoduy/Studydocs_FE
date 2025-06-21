package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationMarkAsReadUseCase

class NotificationMarkAsReadHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationMarkAsReadUseCase = NotificationMarkAsReadUseCase()
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.MarkAsRead

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        val markAsReadIntent = intent as NotificationIntent.MarkAsRead
        notificationMarkAsReadUseCase.invoke(markAsReadIntent.notificationId)
            .onSuccess { success ->
                if (success) {
                    setResult(NotificationResult.MarkAsRead(markAsReadIntent.notificationId))
                } else {
                    setResult(NotificationResult.Error("Failed to mark notification as read."))
                }
            }
            .onFailure { error ->
                setResult(NotificationResult.Error(error.message ?: "An unknown error occurred."))
            }
    }
}