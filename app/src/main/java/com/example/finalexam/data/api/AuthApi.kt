package com.example.finalexam.data.api

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.ForgotPassword
import com.example.finalexam.entity.Login
import com.example.finalexam.entity.Register
import retrofit2.http.Body
import retrofit2.http.POST

// Định nghĩa các endpoint cho Auth, truyền/nhận dữ liệu JSON
interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: Login): BaseResponse<String> // trả về userId/token

    @POST("/auth/register")
    suspend fun register(@Body request: Register): BaseResponse<String>
}