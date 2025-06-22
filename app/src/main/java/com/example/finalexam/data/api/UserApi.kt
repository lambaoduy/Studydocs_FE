package com.example.finalexam.data.api

import com.example.finalexam.data.request.RegisterRequest
import com.example.finalexam.data.request.UpdateFcmTokenRequest
import com.example.finalexam.data.request.UpdateUserRequest
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApi {
    @GET("/user")
    suspend fun getProfile(): BaseResponse<User>

    @PUT("/user")
    suspend fun updateProfile(@Body request: UpdateUserRequest): BaseResponse<Void>

    @PATCH("/user/fcm-token")
    suspend fun updateFcmToken(@Body request: UpdateFcmTokenRequest): BaseResponse<Void>

    @POST("/user/register")
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String>

    @DELETE("/user/delete-fcm-token")
    suspend fun deleteFcmToken(@Query("token") fcmToken: String): BaseResponse<Void>



}
