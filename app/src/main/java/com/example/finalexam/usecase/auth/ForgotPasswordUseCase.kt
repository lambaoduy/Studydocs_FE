package com.example.finalexam.usecase.auth

import com.example.finalexam.config.FirebaseConfig
import kotlinx.coroutines.tasks.await

class ForgotPasswordUseCase {
    private val firebaseAuth = FirebaseConfig.firebaseAuth
    suspend fun invoke(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}