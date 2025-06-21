package com.example.finalexam.data.response


data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T
)