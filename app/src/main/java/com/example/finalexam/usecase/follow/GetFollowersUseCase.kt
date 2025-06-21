package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.entity.Follower
import com.example.finalexam.network.RetrofitClient
import retrofit2.HttpException

class GetFollowersUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)

    suspend fun invoke(targetId: String, targetType: FollowType): Result<List<Follower>> {
        return try {
            val response = followApi.getFollowers(targetId, targetType)
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