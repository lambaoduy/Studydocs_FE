package com.example.finalexam.usecase.profile

import com.example.finalexam.data.api.UserApi
import com.example.finalexam.entity.User
import com.example.finalexam.network.RetrofitClient

class LoadProfileUseCase {
    private val userApi = RetrofitClient.createApi(UserApi::class.java)

    // Lấy thông tin user từ Firestore
    suspend fun invoke(): Result<User> {
        return try {
            val user = userApi.getProfile().data
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}