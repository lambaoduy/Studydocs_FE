package com.example.finalexam.usecase.notification

import com.example.finalexam.data.api.NotificationApi
import com.example.finalexam.network.RetrofitClient

class NotificationMarkAsReadAllUseCase {
    private val notificationApi = RetrofitClient.createApi(NotificationApi::class.java)

    suspend fun invoke() {
        var response = notificationApi.readAllNotification()
        if (response.status != 200) {
            throw Exception(response.message)
        }
    }
}