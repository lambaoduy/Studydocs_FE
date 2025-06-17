package com.example.finalexam.entity


import com.example.finalexam.data.enums.FollowType

data class Following(
    val followingId: String,
    val targetId: String,
    val name: String,
    val avatarUrl: String,
    val notifyEnables: Boolean,
    val targetType: FollowType
)

