package com.example.finalexam.state
//file này duy viết
data class HomeState(
    val isLoading: Boolean = false,
    val todos: List<String> = emptyList(),//String tại chưa có model sau này sửa sau
    val error: String? = null
)