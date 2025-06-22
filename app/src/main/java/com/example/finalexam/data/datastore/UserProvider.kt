package com.example.finalexam.data.datastore

object UserProvider {
    private var token: String? = null
    private var userId: String? = null

    fun setUserId(value: String) {
        userId = value
    }

    fun getUserId(): String? = userId

    fun setToken(value: String) {
        token = value
    }
    fun clear() {
        userId = null
        token = null
    }

    fun getToken(): String? = token
}