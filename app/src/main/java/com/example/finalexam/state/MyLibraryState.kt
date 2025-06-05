// Định nghĩa State cho màn hình MyLibrary
// State này sẽ được ViewModel cập nhật và View sử dụng để hiển thị UI
package com.example.finalexam.state

import com.example.finalexam.entity.Document


data class MyLibraryState(
    val isLoading: Boolean = false, // Trạng thái đang tải dữ liệu
    val documents: List<Document> = emptyList(), // Danh sách tài liệu
    val searchQuery: String = "", // Từ khóa tìm kiếm hiện tại
    val error: String? = null // Thông báo lỗi (nếu có)
) 