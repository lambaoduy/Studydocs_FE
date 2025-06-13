package com.example.finalexam.reduce

import com.example.finalexam.result.FollowResult
import com.example.finalexam.state.FollowState

class FollowReducer {
    fun reduce(state: FollowState, result: FollowResult): FollowState =
        when (result) {
            is FollowResult.Loading -> state.copy(isLoading = true)
            is FollowResult.Error -> state.copy(isLoading = false, errorMessage = result.message)
            is FollowResult.FollowActionResult -> state.copy(
                isLoading = false,
                followings = state.followings + result.following
            )

            is FollowResult.UnFollowActionResult -> state.copy(
                isLoading = false,
                followings = state.followings.filter { it.followingId != result.followingId }
            )

            is FollowResult.GetFollowers -> state.copy(
                isLoading = false,
                followers = result.followers
            )

            is FollowResult.GetFollowings -> state.copy(
                isLoading = false,
                followings = result.followings
            )

            is FollowResult.ToggleNotifyEnableResult -> state.copy(
                isLoading = false,
                followings = state.followings.map {
                    if (it.followingId == result.followingId) it.copy(
                        notifyEnables = result.notifyEnable
                    ) else it
                })


        }
}