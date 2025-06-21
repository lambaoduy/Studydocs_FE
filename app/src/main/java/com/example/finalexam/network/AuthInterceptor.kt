package com.example.finalexam.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor này sẽ tự động lấy token từ SharedPreferences
 * và gắn vào header Authorization cho mọi request gửi tới backend.
 */
class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val prefs = context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
        val token = prefs.getString("ID_TOKEN", null)
        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}