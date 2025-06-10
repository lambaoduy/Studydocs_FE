package com.example.finalexam.usecase.notification

import com.example.finalexam.data.dao.notification.NotificationDao
import com.example.finalexam.data.dao.notification.impl.NotificationDaoImpl
import com.example.finalexam.entity.Notification
import com.example.finalexam.result.NotificationResult

class ShowNotificationUseCase {
    private val notificationDao: NotificationDao = NotificationDaoImpl()

    suspend fun invoke(viewMode: NotificationResult.ViewMode, userId: String): List<Notification> =
        when (viewMode) {
            NotificationResult.ViewMode.PANEL -> notificationDao.getNotificationsNotRead(userId)
            NotificationResult.ViewMode.SCREEN -> notificationDao.getNotifications(userId)
            else -> emptyList()
        }

}