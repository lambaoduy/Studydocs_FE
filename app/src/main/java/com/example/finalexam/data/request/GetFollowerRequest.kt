package com.example.finalexam.data.request

import com.example.finalexam.data.enums.FollowType

data class GetFollowerRequest(val targetId: String, val type: FollowType) {
}