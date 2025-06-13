package com.example.finalexam.data.api

import com.example.finalexam.data.request.UpdateFcmTokenRequest
import com.example.finalexam.data.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/fcmToken")
    suspend fun updateFcmToken(@Body updateFcmTokenRequest: UpdateFcmTokenRequest): BaseResponse<Boolean>


}
