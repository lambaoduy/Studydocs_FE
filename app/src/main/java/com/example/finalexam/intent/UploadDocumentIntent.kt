// Định nghĩa các Intent (hành động) cho màn hình UploadDocument
package com.example.finalexam.intent
sealed class UploadDocumentIntent {
    // Hành động chọn tài liệu để upload
    data class SelectDocument(val documentId: String) : UploadDocumentIntent()
    // Hành động chọn trường đại học
    data class SelectUniversity(val universityId: String) : UploadDocumentIntent()
    // Hành động chọn khóa học trong trường
    data class SelectCourse(val courseIndex: Int) : UploadDocumentIntent()
    // Hành động nhấn nút upload
    object Upload : UploadDocumentIntent()
    // Hành động quay lại
    object Back : UploadDocumentIntent()
}

