package com.example.finalexam.usecase

import com.example.finalexam.api.AuthApi
import com.example.finalexam.dto.ForgotPasswordDTO
import com.example.finalexam.dto.LoginDTO
import com.example.finalexam.dto.RegisterDTO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthUseCase(private val authApi: AuthApi) {
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val authResult = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .await()
            val idToken = authResult.user?.getIdToken(true)?.await()?.token
                ?: return Result.failure(Exception("Không lấy được token"))
            // Gửi token lên backend
            val response = authApi.login(LoginDTO(idToken))
            if (response.success) Result.success(Unit)
            else Result.failure(Exception(response.message ?: "Lỗi backend"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(username: String, email: String, password: String): Result<Unit> {
        return try {
            val authResult = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .await()
            val idToken = authResult.user?.getIdToken(true)?.await()?.token
                ?: return Result.failure(Exception("Không lấy được token"))
            // Gửi token lên backend
            val response = authApi.register(RegisterDTO(idToken))
            if (response.success) Result.success(Unit)
            else Result.failure(Exception(response.message ?: "Lỗi backend"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun forgotPassword(email: String): Result<Unit> {
        return try {
            // Gửi email reset password qua Firebase
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
            // Gửi email lên backend nếu cần (nếu BE cần log hoặc xử lý gì thêm)
            val response = authApi.forgotPassword(ForgotPasswordDTO(email))
            if (response.success) Result.success(Unit)
            else Result.failure(Exception(response.message ?: "Lỗi backend"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}