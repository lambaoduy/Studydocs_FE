package com.example.finalexam.usecase.notification

import com.example.finalexam.data.api.NotificationApi
import com.example.finalexam.network.RetrofitClient

class NotificationDeleteUseCase {
    private val notificationApi = RetrofitClient.createApi(NotificationApi::class.java)

    suspend fun invoke(notificationId: String) {
        var response = notificationApi.deleteNotification(notificationId)
        if (response.status != 200) {
            throw Exception(response.message)
        }
    }
}