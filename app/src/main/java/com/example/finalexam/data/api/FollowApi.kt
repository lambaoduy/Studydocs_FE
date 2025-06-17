package com.example.finalexam.data.api

import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.data.request.FollowRequest
import com.example.finalexam.data.request.ToggleNotifyRequest
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.Follower
import com.example.finalexam.entity.Following
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface FollowApi {
    @POST("/user/follow")
    suspend fun follow(@Body followRequest: FollowRequest): BaseResponse<Following>

    @POST("/user/unFollow")
    suspend fun unFollow(@Body followingId: String): BaseResponse<String>

    @PATCH("/user/follow")
    suspend fun toggleNotify(@Body toggleNotifyRequest: ToggleNotifyRequest): BaseResponse<Boolean>

    @GET("/user/follower")
    suspend fun getFollowers(
        @Query("targetId") targetId: String,
        @Query("type") type: FollowType
    ): BaseResponse<List<Follower>>

    @GET("/user/following")
    suspend fun getFollowings(): BaseResponse<List<Following>>


}