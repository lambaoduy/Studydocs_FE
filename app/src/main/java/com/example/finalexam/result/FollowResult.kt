package com.example.finalexam.result

import com.example.finalexam.entity.Follower
import com.example.finalexam.entity.Following


sealed class FollowResult {
    data object Loading : FollowResult()
    data object FollowActionResult : FollowResult()

    data class UnFollowActionResult(val followingId: String) : FollowResult()

    data class ToggleNotifyEnableResult(val followingId: String, val notifyEnable: Boolean) :
        FollowResult()

    data class Error(val message: String) : FollowResult()
    data class GetFollowers(val followers: List<Follower>) : FollowResult()
    data class GetFollowings(val followings: List<Following>) : FollowResult()
}