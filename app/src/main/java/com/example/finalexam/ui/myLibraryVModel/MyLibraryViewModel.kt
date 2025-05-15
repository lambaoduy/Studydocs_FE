package com.example.finalexam.ui.myLibraryVModel

import androidx.lifecycle.ViewModel
import com.example.finalexam.ui.myLibraryVModel.Document

class MyLibraryViewModel : ViewModel() {
    private val _documents = mutableListOf<Document>()
    val documents: List<Document> get() = _documents

    init {
        // Sample data for preview
        _documents.addAll(listOf(
            Document("1", "Tài liệu mẫu 1", "Tác giả A", "01/01/2023"),
            Document("2", "Tài liệu mẫu 2", "Tác giả B", "02/01/2023")
        ))
    }

    fun uploadDocument() {
        val newDoc = Document(
            id = (_documents.size + 1).toString(),
            title = "Tài liệu mới",
            author = "Người dùng",
            createdDate = "Hôm nay"
        )
        _documents.add(newDoc)
    }


    fun filterDocuments(query: String): List<Document> {
        return if (query.isEmpty()) documents
        else documents.filter { it.title.contains(query, true) }
    }
}