package com.example.finalexam.usecase.profile

import android.util.Log
import com.example.finalexam.data.api.UserApi
import com.example.finalexam.data.datastore.UserPreferences
import com.example.finalexam.network.RetrofitClient

class LogoutUseCase {
    private val userApi = RetrofitClient.createApi(UserApi::class.java)
    suspend fun invoke(): Result<Unit> {
        return try {
            val fcmToken = UserPreferences.getFcmToken()
            if (fcmToken != null) {
                try {
                    userApi.deleteFcmToken(fcmToken)
                } catch (e: Exception) {
                    // Log the error, but don't block logout
                    Log.e("LogoutUseCase", "Failed to delete FCM token", e)
                }
            }
            UserPreferences.clear()
            Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}