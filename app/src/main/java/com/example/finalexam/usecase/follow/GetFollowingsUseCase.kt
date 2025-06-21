package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.entity.Following
import com.example.finalexam.network.RetrofitClient
import retrofit2.HttpException

class GetFollowingsUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)

    suspend fun invoke(): Result<List<Following>> {
        return try {
            val response = followApi.getFollowings()
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