package com.example.finalexam.usecase.notification

import com.example.finalexam.data.api.NotificationApi
import com.example.finalexam.entity.Notification
import com.example.finalexam.network.RetrofitClient
import retrofit2.HttpException

class NotificationInitialUseCase {
    private val notificationApi = RetrofitClient.createApi(NotificationApi::class.java)

    suspend fun invoke(): Result<List<Notification>> {
        return try {
            val response = notificationApi.getNotifications()
            if (response.status == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Failed to get notifications."))
            }
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}