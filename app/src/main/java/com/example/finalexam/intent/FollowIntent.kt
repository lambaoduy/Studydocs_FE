package com.example.finalexam.intent

import com.example.finalexam.data.enums.FollowType

sealed class FollowIntent {
    data object GetFollowings : FollowIntent()
    data class Follow(val targetId: String, val targetType: FollowType) : FollowIntent()

    data class Unfollow(val followingId: String) : FollowIntent()

    data class ToggleNotifyEnable(val followingId: String, val notifyEnable: Boolean) :
        FollowIntent()

    data class GetFollowers(val targetId: String, val targetType: FollowType) : FollowIntent()


}
