package com.example.finalexam.usecase.notification

import com.example.finalexam.data.dao.notification.NotificationDao
import com.example.finalexam.data.dao.notification.impl.NotificationDaoImpl

class OpenNotificationUseCase {
    private val notificationDao: NotificationDao = NotificationDaoImpl()

    suspend fun invoke(notificationId: String): String =
        notificationDao.getNotification(notificationId).documentId

}