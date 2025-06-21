package com.example.finalexam.usecase.notification

import com.example.finalexam.data.api.NotificationApi
import com.example.finalexam.network.RetrofitClient
import retrofit2.HttpException

class NotificationMarkAsReadAllUseCase {
    private val notificationApi = RetrofitClient.createApi(NotificationApi::class.java)

    suspend fun invoke(): Result<Boolean> {
        return try {
            val response = notificationApi.readAllNotification()
            if (response.status == 200) {
                Result.success(response.data ?: false)
            } else {
                Result.failure(Exception(response.message ?: "Failed to mark all notifications as read."))
            }
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}