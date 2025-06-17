package com.example.finalexam.handler.notification

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationMarkAsReadUseCase
import com.google.gson.Gson
import retrofit2.HttpException

class NotificationMarkAsReadHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationMarkAsReadUseCase = NotificationMarkAsReadUseCase()
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.MarkAsRead

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        try {
            val notificationMarkAsReadIntent = intent as NotificationIntent.MarkAsRead
            notificationMarkAsReadUseCase.invoke(
                notificationMarkAsReadIntent.notificationId
            )
            setResult(NotificationResult.MarkAsRead(notificationMarkAsReadIntent.notificationId))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = try {
                val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
                errorResponse?.message ?: "Unknown error"
            } catch (ex: Exception) {
                "Unknown error"
            }
            setResult(NotificationResult.Error(message))
        }
    }
}