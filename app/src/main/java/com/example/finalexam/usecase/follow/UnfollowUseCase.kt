package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.network.RetrofitClient

class UnfollowUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)
    suspend fun invoke(followingId: String): String {
        var response = followApi.unFollow(followingId)
        return response.data
        if (response.status != 200)
            throw Exception(response.message)

    }


}