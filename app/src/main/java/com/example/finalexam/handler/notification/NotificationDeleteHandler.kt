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
        try {
            val notificationDeleteIntent = intent as NotificationIntent.Delete
            notificationDeleteUseCase.invoke(notificationDeleteIntent.notificationId)
            setResult(NotificationResult.Delete(notificationDeleteIntent.notificationId))
        } catch (e: Exception) {
            setResult(NotificationResult.Error(e.message ?: "Unknown error"))
        }
    }
}