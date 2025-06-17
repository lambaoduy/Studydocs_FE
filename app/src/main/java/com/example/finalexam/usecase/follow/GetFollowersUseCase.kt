package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.entity.Follower
import com.example.finalexam.network.RetrofitClient

class GetFollowersUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)
    suspend fun invoke(targetId: String, targetType: FollowType): List<Follower> {
        val response = followApi.getFollowers(targetId, targetType)
        if (response.status != 200) {
            throw Exception(response.message)
        }
        return response.data
    }

}