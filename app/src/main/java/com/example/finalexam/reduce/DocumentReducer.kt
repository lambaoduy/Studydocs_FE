package com.example.finalexam.reduce

import com.example.finalexam.result.DocumentResult
import com.example.finalexam.state.DocumentState

class DocumentReducer {
    fun reduce(state: DocumentState, result: DocumentResult): DocumentState {
        return when (result) {
            is DocumentResult.Loaded -> state.copy(
                isLoading = false,
                document = result.document,
                errorMessage = null
            )
            is DocumentResult.DownloadUrl -> state.copy(
                downloadUrl = result.url,
                errorMessage = null
            )
            is DocumentResult.Liked -> state.copy(
                isLiked = true,
                errorMessage = null
            )
            is DocumentResult.Unliked -> state.copy(
                isLiked = false,
                errorMessage = null
            )
            is DocumentResult.Followed -> state.copy(isFollowed = true)
            is DocumentResult.Unfollowed -> state.copy(isFollowed = false)
            is DocumentResult.Error -> state.copy(
                isLoading = false,
                errorMessage = result.message
            )
            else -> state
        }
    }
}