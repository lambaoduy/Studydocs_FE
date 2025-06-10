package com.example.finalexam.usecase.notification

import com.example.finalexam.data.dao.notification.NotificationDao
import com.example.finalexam.data.dao.notification.impl.NotificationDaoImpl

class NotificationDeleteAllUseCase {
    private val notificationDao: NotificationDao = NotificationDaoImpl()

    suspend fun invoke(userId: String) {
        notificationDao.deleteAll(userId)
    }
}