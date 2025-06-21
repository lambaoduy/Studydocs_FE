package com.example.finalexam.usecase.auth

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

class LogoutUseCase(private val context: Context) {
    fun logout() : Result<Unit> {
        return try {
            FirebaseAuth.getInstance().signOut()
            val prefs = context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
            prefs.edit().remove("ID_TOKEN").apply()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

