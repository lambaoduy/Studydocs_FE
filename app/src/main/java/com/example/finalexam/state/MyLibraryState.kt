// Định nghĩa State cho màn hình MyLibrary
// State này sẽ được ViewModel cập nhật và View sử dụng để hiển thị UI
package com.example.finalexam.state

import com.example.finalexam.entity.Document


data class MyLibraryState(
    val isLoading: Boolean = false,
    val documents: List<Document> = emptyList(),
    val searchQuery: String = "",
    val selectedDocument: Document? = null,
    val error: String? = null
)
