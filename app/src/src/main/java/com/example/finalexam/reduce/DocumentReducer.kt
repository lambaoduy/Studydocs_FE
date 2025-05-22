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
            isLiked = result.document.likes?.any { it.userId == "currentUserId" } ?: false // Giả định userId hiện tại
        )
        is DocumentResult.DownloadUrl -> state.copy(downloadUrl = result.url)
        is DocumentResult.ActionSuccess -> state // Cập nhật state nếu cần (ví dụ: toggle isLiked/isBookmarked)
        is DocumentResult.Error -> state.copy(isLoading = false, errorMessage = result.message)
    }
}