package com.example.finalexam.usecase.mylibrary

import com.example.finalexam.entity.Document
import com.example.finalexam.state.MyLibraryState
import kotlinx.coroutines.flow.StateFlow

class SearchDocumentsUseCase() {
    suspend fun findByFilters(
        keyword: String?,
        school: String?,
        subject: String?,
        cacheList: List<Document>?
    ): List<Document> {
        // Dữ liệu ban đầu từ cache
        var result = cacheList ?: emptyList()

        // Lọc từ cache nếu có
        if (result.isNotEmpty()) {
            if (!keyword.isNullOrBlank()) {
                result = result.filter { it.title.contains(keyword, ignoreCase = true) }
            }
            if (!school.isNullOrBlank()) {
                result = result.filter { it.university.contains(school, ignoreCase = true) }
            }
            if (!subject.isNullOrBlank()) {
                result = result.filter { it.subject.contains(subject, ignoreCase = true) }
            }
            return result
        }
     return result
    }
} 