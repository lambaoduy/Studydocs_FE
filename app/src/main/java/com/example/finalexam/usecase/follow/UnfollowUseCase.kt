package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.network.RetrofitClient
import retrofit2.HttpException

class UnfollowUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)

    suspend fun invoke(followingId: String): Result<String> {
        return try {
            val response = followApi.unFollow(followingId)
            if (response.status == 200) {
                Result.success(response.data)
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