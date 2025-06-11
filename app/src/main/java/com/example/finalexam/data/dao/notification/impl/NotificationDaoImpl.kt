package com.example.finalexam.data.dao.notification.impl

import com.example.finalexam.entity.Notification
import com.example.finalexam.data.dao.notification.NotificationDao

class NotificationDaoImpl : NotificationDao {

    private suspend fun markAllAsRead() {

    }

    override suspend fun getNotifications(userId: String): List<Notification> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotificationsNotRead(userId: String): List<Notification> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotification(notificationId: String): Notification {
        TODO("Not yet implemented")
    }
}