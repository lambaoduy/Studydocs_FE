package com.example.finalexam.usecase.auth

import com.example.finalexam.data.api.AuthApi
import com.example.finalexam.entity.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginUseCase(private val authApi: AuthApi) {
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val authResult = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .await()
            val firebaseUser = authResult.user
                ?: return Result.failure(Exception("Không tìm thấy người dùng Firebase"))
            // Gọi API backend với email và password
            val response = authApi.login(com.example.finalexam.entity.Login(email, password))
            if (response.status == 200) {
                val userId = response.data
                Result.success(User(userId = userId, email = email))
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
