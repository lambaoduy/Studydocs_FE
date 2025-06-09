package com.example.finalexam.usecase.follower

import com.example.finalexam.data.api.UserApi
import com.example.finalexam.data.request.FollowRequest
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.network.RetrofitClient

class FollowActionUseCase {
    private val userApi: UserApi = RetrofitClient.createApi(UserApi::class.java)
    suspend fun invoke(followerId: String, followeeId: String, isFollowing: Boolean) {
        var response: BaseResponse<Boolean> = if (isFollowing)
            userApi.unFollow(FollowRequest(followerId, followeeId))
        else
            userApi.follow(FollowRequest(followerId, followeeId))
        if (response.stautus != 200)
            throw Exception(response.message)

    }


}