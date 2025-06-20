package com.example.finalexam.handler.notification

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationDeleteAllUseCase
import com.google.gson.Gson
import retrofit2.HttpException

class NotificationDeleteAllHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationDeleteAllUseCase = NotificationDeleteAllUseCase()
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.DeleteAll

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        try {
            notificationDeleteAllUseCase.invoke()
            setResult(NotificationResult.DeleteAll)
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