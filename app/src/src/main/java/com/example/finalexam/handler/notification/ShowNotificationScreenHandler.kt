package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.ShowNotificationUseCase

class ShowNotificationScreenHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val showNotificationUseCase = ShowNotificationUseCase()

    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.ShowScreen

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        val showScreenIntent = intent as NotificationIntent.ShowScreen
        setResult(NotificationResult.Load)
        try {
            setResult(
                NotificationResult.Show(
                    showNotificationUseCase.invoke(
                        NotificationResult.ViewMode.SCREEN,
                        showScreenIntent.userid
                    ),
                    NotificationResult.ViewMode.SCREEN
                )
            )
        } catch (e: Exception) {
            setResult(NotificationResult.Error(e.message ?: "Unknown error"))
        }
    }
}