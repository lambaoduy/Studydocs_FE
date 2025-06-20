package com.example.finalexam.data.request

import com.example.finalexam.data.enums.FollowType

data class FollowRequest(
    val targetId: String,
    val type: FollowType

)