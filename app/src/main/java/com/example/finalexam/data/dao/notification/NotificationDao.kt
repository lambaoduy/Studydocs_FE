package com.example.finalexam.data.dao.notification

import com.example.finalexam.entity.Notification

interface NotificationDao {
    suspend fun getNotifications(userId: String): List<Notification>
    suspend fun getNotificationsNotRead(userId: String): List<Notification>
    suspend fun getNotification(notificationId: String): Notification
    suspend fun markAsReadAll(userId: String)
    suspend fun markAsRead(notificationId: String)
    suspend fun delete(notificationId: String)
    suspend fun deleteAll(userId: String)

}