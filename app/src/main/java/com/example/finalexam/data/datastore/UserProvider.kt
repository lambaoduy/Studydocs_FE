package com.example.finalexam.data.datastore

import com.example.finalexam.entity.User

object UserProvider {
    private var token: String? = null
    private var userId: String? = null
    private var user: User? = null

    fun setUserId(value: String) {
        userId = value
    }

    fun setUser(value: User) {
        user = value
    }

    fun getUser(): User? = user

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