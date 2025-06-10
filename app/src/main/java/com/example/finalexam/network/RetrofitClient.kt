package com.example.finalexam.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Địa chỉ gốc của backend API, cần thay đổi cho đúng với server thật khi deploy
    private const val BASE_URL = "http://localhost:8080/..."

    /**
     * Hàm tạo instance của API service với Retrofit.
     * @param api: Interface định nghĩa các API endpoint (ví dụ: ApiService::class.java)
     * @param context: Context để lấy SharedPreferences phục vụ cho AuthInterceptor
     */
    fun <T> createApi(api: Class<T>, context: Context): T {
        // Tạo OkHttpClient và thêm AuthInterceptor để tự động gắn token vào header Authorization
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context)) // Interceptor lấy token từ SharedPreferences
            .build()
        // Tạo Retrofit instance với baseUrl, OkHttpClient và converter cho JSON
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Địa chỉ gốc của backend
            .client(client) // OkHttpClient đã có Interceptor
            .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON sang object Kotlin
            .build()
        // Trả về instance của API service
        return retrofit.create(api)
    }
}