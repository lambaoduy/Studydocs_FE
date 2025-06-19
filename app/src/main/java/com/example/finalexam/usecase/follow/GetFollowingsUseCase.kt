package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.entity.Following
import com.example.finalexam.network.RetrofitClient

class GetFollowingsUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)
    suspend fun invoke(): List<Following> {
        val response = followApi.getFollowings()
        if (response.status != 200) {
            throw Exception(response.message)
        }
        return response.data
    }
}