package com.example.finalexam.result

import com.example.finalexam.entity.User


sealed class FollowerResult {
    data object Loading : FollowerResult()
    data class FollowActionResult(val isFollowing: Boolean) : FollowerResult()
    data class Error(val message: String) : FollowerResult()
    data class GetFollowers(val followers: List<User>) : FollowerResult()
    data class GetFollowees(val followees: List<User>) : FollowerResult()
}