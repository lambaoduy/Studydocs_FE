// Định nghĩa các Intent (hành động) cho màn hình MyLibrary
package com.example.finalexam.intent
import com.example.finalexam.entity.Document

sealed class MyLibraryIntent {
    // Hành động tìm kiếm tài liệu
    data class Search(val query: String) : MyLibraryIntent()
    //  lọc theo university
    data class FilterByUniversity(val university: String) : MyLibraryIntent()
    //lọc theo subject
    data class FilterBySubject(val subject: String) : MyLibraryIntent()
    // Hành động tải lại danh sách tài liệu
    data object Refresh : MyLibraryIntent()
    // Hành động nhấn nút upload tài liệu
    data object OnUploadClicked : MyLibraryIntent()
    // Hành động chọn một tài liệu (có thể dùng cho xem chi tiết hoặc thao tác khác)
    data class SelectDocument(val document: Document) : MyLibraryIntent()
    // Hành động quay về trang chủ
    data object NavigateToHome : MyLibraryIntent()
}
