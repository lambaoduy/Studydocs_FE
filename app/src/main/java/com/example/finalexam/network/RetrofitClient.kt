package com.example.finalexam.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://192.168.217.125:8080/"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //dùng UnsafeOkHttpClient để bỏ qua xác minh SSL
            .client(UnsafeOkHttpClient.get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createApi(api: Class<T>): T {
        return retrofit.create(api)
    }

}