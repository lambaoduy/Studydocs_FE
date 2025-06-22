package com.example.finalexam.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://studydocsbe-production.up.railway.app"
    private var retrofit: Retrofit? = null
//    https://studydocsbe-production.up.railway.app
    fun init() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(UnsafeOkHttpClient.get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }


    fun <T> createApi(api: Class<T>): T {
        if (retrofit == null) {
            throw IllegalStateException("RetrofitClient chưa được khởi tạo. Gọi RetrofitClient.init(context) trước.")
        }
        return retrofit!!.create(api)
    }
}