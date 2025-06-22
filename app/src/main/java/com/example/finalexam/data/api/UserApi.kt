package com.example.finalexam.data.api

import com.example.finalexam.data.request.RegisterRequest
import com.example.finalexam.data.request.UpdateUserRequest
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {
    @GET("/user")
    suspend fun getProfile(): BaseResponse<User>

    @PUT("/user")
    suspend fun updateProfile(@Body request: UpdateUserRequest): BaseResponse<Void>

    @PATCH("/user/fcm-token")
    suspend fun updateFcmToken(@Body fcmToken: String): BaseResponse<Void>

    @POST("/user/register")
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String>

    @DELETE("/user/fcm-token")
    suspend fun deleteFcmToken(@Body fcmToken: String): BaseResponse<Void>

}
