package com.example.finalexam.data.api

import com.example.finalexam.data.request.RegisterRequest
import com.example.finalexam.data.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserApi {
    @PATCH("/user/fcm-token")
    suspend fun updateFcmToken(@Body fcmToken: String): BaseResponse<Void>
    @POST("/user/register")
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String>

}
