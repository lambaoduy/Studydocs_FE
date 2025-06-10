package com.example.finalexam.usecase.notification

import com.example.finalexam.data.dao.notification.NotificationDao
import com.example.finalexam.data.dao.notification.impl.NotificationDaoImpl
import com.example.finalexam.entity.Notification

class NotificationInitialUseCase {
    private val notificationDao: NotificationDao = NotificationDaoImpl()

    suspend fun invoke(userId: String): List<Notification> {
        return notificationDao.getNotifications(userId)
    }
}