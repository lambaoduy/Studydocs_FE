package com.example.finalexam.data.api

import com.example.finalexam.data.request.FollowRequest
import com.example.finalexam.data.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/follow")
    suspend fun follow(@Body followRequest: FollowRequest): BaseResponse<Boolean>
    @POST("/user/unFollow")
    suspend fun unFollow(@Body followRequest: FollowRequest): BaseResponse<Boolean>
}
