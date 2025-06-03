package com.example.finalexam.usecase

// thiện làm: UseCase cho Auth (Login/Register), giả lập xử lý
class AuthUseCase {
    suspend fun login(email: String, password: String): Result<Unit> {
        // TODO: Kết nối Firebase Auth
        return if (email == "test@gmail.com" && password == "123456") Result.success(Unit)
        else Result.failure(Exception("Sai tài khoản hoặc mật khẩu"))
    }

    suspend fun register(username: String, email: String, password: String): Result<Unit> {
        // TODO: Kết nối Firebase Auth
        return if (email.contains("@")) Result.success(Unit)
        else Result.failure(Exception("Email không hợp lệ"))
    }
} 