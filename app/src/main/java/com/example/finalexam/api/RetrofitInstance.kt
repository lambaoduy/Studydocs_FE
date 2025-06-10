package com.example.finalexam.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://studydocs-be-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}
