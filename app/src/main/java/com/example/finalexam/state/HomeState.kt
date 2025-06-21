package com.example.finalexam.state

import com.example.finalexam.entity.Document

//file này duy viết
data class HomeState(
    val listDocument: List<Document>,//danh sách document
    val isLoading: Boolean = false,                 // đang loading
    val error: String? = null ,                      // thông báo lỗi (nếu có)
    val keyword: String? = null,
    val school: String? = null,
    val subject: String? = null
)