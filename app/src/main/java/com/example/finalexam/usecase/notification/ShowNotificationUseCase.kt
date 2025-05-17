package com.example.finalexam.usecase.notification

import com.example.finalexam.entity.Notification

class ShowNotificationUseCase {
    suspend fun invoke(): List<Notification>  {
        return emptyList<Notification>();
    }
}