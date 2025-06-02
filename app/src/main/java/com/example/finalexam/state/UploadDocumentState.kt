// Định nghĩa State cho màn hình UploadDocument
// State này sẽ được ViewModel cập nhật và View sử dụng để hiển thị UI
package com.example.finalexam.state

import com.example.finalexam.ui.myLibraryScreen.University
import com.example.finalexam.ui.myLibraryScreen.UploadDocument

data class UploadDocumentState(
    val documents: List<UploadDocument> = emptyList(), // Danh sách tài liệu có thể upload
    val university: University? = null, // Trường đại học được chọn
    val isUploading: Boolean = false, // Trạng thái đang upload
    val uploadSuccess: Boolean = false, // Kết quả upload
    val error: String? = null // Thông báo lỗi (nếu có)
) 