package com.example.finalexam.usecase.notification

import com.example.finalexam.dao.notification.NotificationDao
import com.example.finalexam.dao.notification.impl.NotificationDaoImpl

class OpenNotificationUseCase {
    private val notificationDao: NotificationDao = NotificationDaoImpl()

    suspend fun invoke(notificationId: String): String =
        notificationDao.getNotification(notificationId).documentId

}