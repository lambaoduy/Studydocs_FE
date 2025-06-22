package com.example.finalexam.reduce

import com.example.finalexam.result.DocumentResult
import com.example.finalexam.state.DocumentState
import com.google.firebase.auth.FirebaseAuth

class DocumentReducer {
    private val currentUserId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    fun reduce(state: DocumentState, result: DocumentResult): DocumentState = when (result) {
        is DocumentResult.Loading -> state.copy(isLoading = true, errorMessage = null)
        is DocumentResult.Loaded -> {
            val userId = currentUserId ?: ""
            state.copy(
                isLoading = false,
                document = result.document,
                errorMessage = null,
                isLiked = result.document.likes?.any { it.userId == userId } ?: false,
                isLoadingLike = false
            )
        }
        is DocumentResult.DownloadUrl -> state.copy(
            downloadUrl = result.url,
            errorMessage = null,
            isLoading = false
        )
        is DocumentResult.Liked -> {
            val userId = currentUserId ?: ""
            state.copy(
                isLiked = true,
                isLoadingLike = false,
                document = state.document?.copy(
                    likes = result.updatedLikes ?: state.document?.likes
                )
            )
        }
        is DocumentResult.Unliked -> {
            val userId = currentUserId ?: ""
            state.copy(
                isLiked = false,
                isLoadingLike = false,
                document = state.document?.copy(
                    likes = result.updatedLikes ?: state.document?.likes
                )
            )
        }
        is DocumentResult.Followed -> state.copy(
            isFollowed = true,
            isLoading = false,
            errorMessage = null
        )
        is DocumentResult.Unfollowed -> state.copy(
            isFollowed = false,
            isLoading = false,
            errorMessage = null
        )
        is DocumentResult.Error -> state.copy(
            isLoading = false,
            isLoadingLike = false,
            errorMessage = result.message
        )
    }
}