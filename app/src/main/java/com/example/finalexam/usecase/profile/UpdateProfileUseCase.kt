package com.example.finalexam.usecase.profile

import com.example.finalexam.data.api.UserApi
import com.example.finalexam.data.request.UpdateUserRequest
import com.example.finalexam.entity.User
import com.example.finalexam.network.RetrofitClient

// UseCase cho chức năng cập nhật và lấy profile từ Firebase
class UpdateProfileUseCase {
    private val userApi = RetrofitClient.createApi(UserApi::class.java)

    // Cập nhật thông tin user lên Firestore
    suspend fun invoke(user: User): Result<Unit> {
        return try {
            userApi.updateProfile(UpdateUserRequest(user.fullName, user.avatarUrl))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}