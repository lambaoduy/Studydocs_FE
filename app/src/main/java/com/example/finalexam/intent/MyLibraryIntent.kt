// Định nghĩa các Intent (hành động) cho màn hình MyLibrary
package com.example.finalexam.intent
import com.example.finalexam.entity.Document
import com.example.finalexam.intent.MyLibraryIntent

sealed class MyLibraryIntent {
    // Hành động tìm kiếm tài liệu
    data class Search(val query: String) : MyLibraryIntent()
    // Hành động tải lại danh sách tài liệu
    object Refresh : MyLibraryIntent()
    // Hành động nhấn nút upload tài liệu
    object OnUploadClicked : MyLibraryIntent()
    // Hành động chọn một tài liệu (có thể dùng cho xem chi tiết hoặc thao tác khác)
    data class SelectDocument(val document: Document) : MyLibraryIntent()
}
