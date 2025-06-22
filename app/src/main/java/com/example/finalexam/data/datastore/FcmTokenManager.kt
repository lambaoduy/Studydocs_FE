package com.example.finalexam.data.datastore

import android.util.Log
import com.example.finalexam.data.api.UserApi
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object FcmTokenManager {
    fun syncTokenIfNeeded(userApi: UserApi) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newToken = FirebaseMessaging.getInstance().token.await()
                val savedToken = UserPreferences.getFcmToken()

                if (savedToken != newToken) {
                    userApi.updateFcmToken(newToken)
                    UserPreferences.saveFcmToken(newToken)
                }
            } catch (e: Exception) {
                Log.e("FCM", "Error syncing FCM token", e)
            }
        }
    }
}