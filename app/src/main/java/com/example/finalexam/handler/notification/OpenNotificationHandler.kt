package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.OpenNotificationUseCase

class OpenNotificationHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val openNotificationUseCase = OpenNotificationUseCase()

    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.Open

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        val openIntent = intent as NotificationIntent.Open
        try {
            setResult(
                NotificationResult.Open(openNotificationUseCase.invoke(openIntent.notificationId))
            )
        } catch (e: Exception) {
            setResult(NotificationResult.Error(e.message ?: "Unknown error"))
        }
    }
}