package com.example.finalexam.state

import com.example.finalexam.entity.User

data class FollowerState (
    val isLoading: Boolean = false,
    val followers: List<User> = emptyList(),
    val followees: List<User> = emptyList(),
    val successMessage: String? = null,
    val errorMessage: String? = null
)

