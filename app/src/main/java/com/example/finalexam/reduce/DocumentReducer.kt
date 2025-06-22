package com.example.finalexam.reduce

import com.example.finalexam.result.DocumentResult
import com.example.finalexam.state.DocumentState

class DocumentReducer {
    /**
     * Chuyển đổi result thành state mới
     * @param state Trạng thái hiện tại
     * @param result Kết quả từ handler
     * @return Trạng thái mới
     */
    fun reduce(state: DocumentState, result: DocumentResult): DocumentState = when (result) {
        is DocumentResult.Loading -> state.copy(isLoading = true, errorMessage = null)
        is DocumentResult.Loaded -> state.copy(
            isLoading = false,
            document = result.document,
            errorMessage = null,
            isLiked = result.document.likes?.any { it.userId == "currentUserId" } ?: false // Giả định userId hiện tại
        )
        is DocumentResult.DownloadUrl -> state.copy(
            downloadUrl = result.url,
            errorMessage = null, // Xóa errorMessage cũ
            isLoading = false
        )
        is DocumentResult.Liked -> state.copy(isLiked = true)
        is DocumentResult.Unliked -> state.copy(isLiked = false)
        is DocumentResult.Error -> state.copy(
            isLoading = false,
            errorMessage = result.message
        )
    }
}