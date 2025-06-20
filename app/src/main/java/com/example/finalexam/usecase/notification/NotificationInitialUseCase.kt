package com.example.finalexam.usecase.notification

import com.example.finalexam.data.api.NotificationApi
import com.example.finalexam.entity.Notification
import com.example.finalexam.network.RetrofitClient

class NotificationInitialUseCase {
    private val notificationApi = RetrofitClient.createApi(NotificationApi::class.java)

    suspend fun invoke(): List<Notification> {
        var response = notificationApi.getNotifications()
        if (response.status != 200) {
            throw Exception(response.message)
        }
        return response.data
    }
}