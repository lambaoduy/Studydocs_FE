// Định nghĩa các Intent (hành động) cho màn hình UploadDocument
package com.example.finalexam.intent
import com.example.finalexam.intent.MyLibraryIntent
import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.state.MyLibraryState
import com.example.finalexam.state.UploadDocumentState
sealed class UploadDocumentIntent {
    // Hành động mở file picker để chọn tài liệu
    object PickDocument : UploadDocumentIntent()
    // Hành động set tài liệu đã chọn (sau khi pick xong)
    data class SetSelectedDocument(val document:  UploadDocument) : UploadDocumentIntent()
    // Hành động bỏ tài liệu đã chọn
    object RemoveSelectedDocument : UploadDocumentIntent()
    // Hành động chọn trường đại học
    data class SelectUniversity(val universityId: String) : UploadDocumentIntent()
    // Hành động chọn khóa học trong trường
    data class SelectCourse(val courseIndex: Int) : UploadDocumentIntent()
    // Hành động nhấn nút upload
    object Upload : UploadDocumentIntent()
    // Hành động quay lại
    object Back : UploadDocumentIntent()
    // Hành động set tiêu đề
    data class SetTitle(val title: String) : UploadDocumentIntent()
    // Hành động set mô tả
    data class SetDescription(val description: String) : UploadDocumentIntent()
    // Hành động chọn môn học
    data class SelectSubject(val subject: String) : UploadDocumentIntent()
    // Hành động tạo môn học mới
    data class CreateSubject(val subjectName: String) : UploadDocumentIntent()
}

