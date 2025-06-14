package com.example.finalexam.reduce

import com.example.finalexam.result.FollowerResult
import com.example.finalexam.state.FollowerState

class FollowerReducer {
    fun reduce(state: FollowerState, result: FollowerResult): FollowerState =
        when (result) {
            is FollowerResult.FollowActionResult -> state.copy(
                isLoading = false,
                successMessage = if (result.isFollowing) "Followed" else "Unfollowed"
            )

            is FollowerResult.GetFollowers -> state.copy(
                followers = result.followers,
                isLoading = false
            )

            is FollowerResult.GetFollowees -> state.copy(
                followees = result.followees,
                isLoading = false
            )

            is FollowerResult.Error -> state.copy(errorMessage = result.message, isLoading = false)
            is FollowerResult.Loading -> state.copy(isLoading = true)

        }
}