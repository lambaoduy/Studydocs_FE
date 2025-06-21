package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.data.request.ToggleNotifyRequest
import com.example.finalexam.network.RetrofitClient
import retrofit2.HttpException

class ToggleNotifyEnableUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)

    suspend fun invoke(followingId: String, notifyEnables: Boolean): Result<Unit> {
        return try {
            val response = followApi.toggleNotify(ToggleNotifyRequest(followingId, notifyEnables))
            if (response.status == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 