package com.example.finalexam.api

data class BaseResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)
