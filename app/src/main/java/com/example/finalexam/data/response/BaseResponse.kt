package com.example.finalexam.data.response


data class BaseResponse<T>(
    val stautus: Int,
    val message: String,
    val data: T
)