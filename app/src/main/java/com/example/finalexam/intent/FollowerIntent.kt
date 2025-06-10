package com.example.finalexam.intent

sealed class FollowerIntent {
    data class Follow(val followerId: String, val followeeId: String) : FollowerIntent()
    data class Unfollow(val followerId: String, val followeeId: String) : FollowerIntent()
    data class GetFollowers(val userId: String) : FollowerIntent()
    data class GetFollowees(val userId: String) : FollowerIntent()
}