package com.example.finalexam.data.api

import com.example.finalexam.data.request.EditProfileRequest
import com.example.finalexam.data.request.FollowRequest
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @POST("/user/follow")
    suspend fun follow(@Body followRequest: FollowRequest): BaseResponse<Boolean>

    @GET("/user/{userId}")
    suspend fun getProfile(@Path("userId") userId: String): BaseResponse<User>

    @PUT("/user/{userId}")
    suspend fun updateProfile(@Path("userId") userId: String, @Body request: EditProfileRequest): BaseResponse<User>

    @GET("/user/{userId}/followers")
    suspend fun getFollowers(@Path("userId") userId: String): BaseResponse<List<User>>

    @GET("/user/{userId}/following")
    suspend fun getFollowing(@Path("userId") userId: String): BaseResponse<List<User>>
}
