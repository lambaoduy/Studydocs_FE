package com.example.finalexam.data.dao.notification

import com.example.finalexam.entity.Notification

interface NotificationDao {
    suspend fun getNotifications(userId: String): List<Notification>
    suspend fun getNotificationsNotRead(userId: String): List<Notification>
    suspend fun getNotification(notificationId: String): Notification
}