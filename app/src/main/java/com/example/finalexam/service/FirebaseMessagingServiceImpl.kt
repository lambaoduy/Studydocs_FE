package com.example.finalexam.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.finalexam.MainActivity
import com.example.finalexam.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "study_channel"
        const val CHANNEL_NAME = "Thông báo chung"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Gửi token lên server nếu cần
        Log.d("FCM", "🆕 New FCM Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        val notification = message.notification

        Log.d(
            "FCM", """
            ✅ Firebase Message Received:
            ├─ From: ${message.from}
            ├─ Data Payload: ${if (data.isNotEmpty()) data else "Không có"}
            ├─ Notification:
            │   ├─ Title: ${notification?.title ?: "Không có"}
            │   ├─ Body : ${notification?.body ?: "Không có"}
            │   └─ Body empty: ${notification?.body.isNullOrEmpty()}
        """.trimIndent()
        )

        val title = notification?.title ?: "Thông báo"
        val body = notification?.body ?: "Bạn có một thông báo mới"

        showNotification(title, body)
    }

    private fun showNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo) // ảnh phải nằm trong res/drawable
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Create channel if Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Kênh thông báo của StudyDocs"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}
