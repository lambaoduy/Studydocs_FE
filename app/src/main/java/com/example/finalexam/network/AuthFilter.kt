package com.example.finalexam.network

import android.content.Context

/**
 * Lớp filter kiểm tra người dùng đã đăng nhập hay chưa.
 * Sử dụng để kiểm tra trạng thái đăng nhập ở bất kỳ đâu trong app.
 */
object AuthFilter {
    /**
     * Trả về true nếu đã đăng nhập (có token), false nếu chưa đăng nhập.
     */
    fun isLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
        val token = prefs.getString("ID_TOKEN", null)
        return !token.isNullOrEmpty()
    }

    /**
     * Nếu chưa đăng nhập thì throw exception, dùng cho các trường hợp cần bắt buộc đăng nhập.
     */
    fun requireLogin(context: Context) {
        if (!isLoggedIn(context)) {
            throw IllegalStateException("Người dùng chưa đăng nhập!")
        }
    }
}

