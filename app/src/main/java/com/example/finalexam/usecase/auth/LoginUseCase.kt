package com.example.finalexam.usecase.auth

class LoginUseCase {
    suspend fun invoke(email: String, password: String): Result<Unit> {
        return Result.success(Unit);
    }
}