package com.example.finalexam.usecase.auth

import com.example.finalexam.data.api.AuthApi
import com.example.finalexam.entity.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class RegisterUseCase(private val authApi: AuthApi) {
    suspend fun register(username: String, email: String, password: String, avatarUrl: String? = null): Result<User> {
        return try {
            val authResult = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .await()
            val firebaseUser = authResult.user
                ?: return Result.failure(Exception("Không lấy được UID từ Firebase"))
            val userId = firebaseUser.uid
            // Sử dụng đúng entity Register cho API backend
            val registerEntity = com.example.finalexam.entity.Register(
                username = username,
                email = email,
                password = password
            )
            val response = authApi.register(registerEntity)
            return if (response.status == 200) {
                Result.success(User(userId = userId, username = username, email = email, avatarUrl = avatarUrl))
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
