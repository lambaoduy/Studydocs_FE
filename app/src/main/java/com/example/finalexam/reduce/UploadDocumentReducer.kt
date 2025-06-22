package com.example.finalexam.reduce

import com.example.finalexam.result.UploadDocumentResult
import com.example.finalexam.state.UploadDocumentState

/**
 * Reducer chịu trách nhiệm cập nhật UploadDocumentState dựa trên UploadDocumentResult
 * 
 * Luồng hoạt động:
 * 1. Nhận UploadDocumentResult từ Handler
 * 2. Nhận UploadDocumentState hiện tại
 * 3. Tạo UploadDocumentState mới dựa trên result type
 * 4. Trả về state mới để ViewModel cập nhật
 * 
 * Nguyên tắc:
 * - State là immutable, luôn tạo state mới thay vì modify state cũ
 * - Mỗi result type có logic xử lý riêng biệt
 * - Error state sẽ clear loading state
 */
class UploadDocumentReducer {
    
    /**
     * Cập nhật state dựa trên result nhận được
     * 
     * @param state State hiện tại
     * @param result Result từ Handler
     * @return State mới sau khi được cập nhật
     */
    fun reduce(state: UploadDocumentState, result: UploadDocumentResult): UploadDocumentState =
        when (result) {
            // ===== LOADING STATE =====
            is UploadDocumentResult.Loading ->
                state.copy(
                    isUploading = true,  // Bắt đầu upload - hiển thị loading
                    error = null         // Clear error message
                )

            // ===== DOCUMENT SELECTION =====
            is UploadDocumentResult.DocumentSelected ->
                state.copy(
                    selectedDocument = result.document,  // Cập nhật document đã chọn
                    error = null                         // Clear error message
                )

            // ===== UPLOAD SUCCESS =====
            is UploadDocumentResult.UploadSuccess ->
                state.copy(
                    isUploading = false,                    // Kết thúc upload
                    uploadSuccess = true,                   // Đánh dấu upload thành công
                    uploadedDocuments = result.uploadedDocuments,  // Lưu danh sách documents đã upload
                    error = null,                           // Clear error message
                    // TODO: Có thể clear form data sau khi upload thành công
                    // title = "",
                    // description = "",
                    // selectedDocument = null
                )

            // ===== ERROR STATE =====
            is UploadDocumentResult.Error ->
                state.copy(
                    isUploading = false,    // Dừng loading khi có lỗi
                    error = result.message  // Lưu error message để hiển thị
                )

            // ===== TEXT INPUT UPDATES =====
            is UploadDocumentResult.SetTitle ->
                state.copy(
                    title = result.title  // Cập nhật title từ user input
                )

            is UploadDocumentResult.SetDescription ->
                state.copy(
                    description = result.description  // Cập nhật description từ user input
                )
        }
}