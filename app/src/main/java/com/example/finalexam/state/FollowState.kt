package com.example.finalexam.state

import com.example.finalexam.entity.Follower
import com.example.finalexam.entity.Following

data class FollowState(
    val isLoading: Boolean = false,
    val followers: List<Follower> = emptyList(),
    val followings: List<Following> = emptyList(),
    val errorMessage: String? = null
)

