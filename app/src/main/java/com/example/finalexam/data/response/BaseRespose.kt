package com.example.finalexam.data.response


data class BaseRespose<T>(
    val stautus: Int,
    val message: String,
    val data: T
)