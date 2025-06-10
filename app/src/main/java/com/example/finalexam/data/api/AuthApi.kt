package com.example.finalexam.api

import com.example.finalexam.dto.LoginDTO
import com.example.finalexam.dto.RegisterDTO
import com.example.finalexam.dto.ForgotPasswordDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginDTO): BaseResponse<String>

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterDTO): BaseResponse<String>

    @POST("/auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordDTO): BaseResponse<String>
}
