package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.data.request.FollowRequest
import com.example.finalexam.data.request.UnfollowRequest
import com.example.finalexam.network.RetrofitClient
import retrofit2.HttpException

class UnfollowUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)
    suspend fun invoke(followingId: String): Result<String> {
        return try {
            println(followingId)
            val response = followApi.unFollow(UnfollowRequest(followingId))
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

    suspend fun invokeByTarget(targetId: String, type: FollowType): Result<Void> {
        return try {
            val response = followApi.unFollowByTarget(FollowRequest(targetId, type))
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