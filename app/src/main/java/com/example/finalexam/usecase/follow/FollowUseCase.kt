package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.data.request.FollowRequest
import com.example.finalexam.entity.Following
import com.example.finalexam.network.RetrofitClient

class FollowUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)
    suspend fun invoke(targetId: String, type: FollowType): Following {
        var response = followApi.follow(FollowRequest(targetId, type))
        return response.data
        if (response.status != 200)
            throw Exception(response.message)

    }
}