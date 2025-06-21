package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationDeleteUseCase

class NotificationDeleteHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationDeleteUseCase = NotificationDeleteUseCase()
    
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.Delete

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        val deleteIntent = intent as NotificationIntent.Delete
        notificationDeleteUseCase.invoke(deleteIntent.notificationId)
            .onSuccess { success ->
                if (success) {
                    setResult(NotificationResult.Delete(deleteIntent.notificationId))
                } else {
                    setResult(NotificationResult.Error("Failed to delete notification."))
                }
            }
            .onFailure { error ->
                setResult(NotificationResult.Error(error.message ?: "An unknown error occurred."))
            }
    }
}