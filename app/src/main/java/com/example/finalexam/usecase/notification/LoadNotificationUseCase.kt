package com.example.finalexam.usecase.notification

import com.example.finalexam.entity.Notification

class LoadNotificationUseCase {
     suspend fun invoke(): List<Notification>  {
        return emptyList<Notification>();
    }

}