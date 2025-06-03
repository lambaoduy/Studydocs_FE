package com.example.finalexam.dao.document

import com.example.finalexam.entity.Document

class DocumentDao {
    // Danh sách document mẫu
    private val sampleDocuments = listOf(
        Document("Duyyyyyy", "Lập trình Kotlin", "Kỹ thuật phần mềm"),
        Document("Heloooo", "Cấu trúc dữ liệu", "Khoa học máy tính"),
        Document("androiddd", "Toán rời rạc", "Toán học"),
    )
    fun getAll():List<Document>{
        return sampleDocuments
    }
    fun getDocumentsByKeyword(keyword: String): List<Document> {
        return sampleDocuments.filter { doc ->
            doc.title.contains(keyword, ignoreCase = true) ||
                    doc.subject.contains(keyword, ignoreCase = true) ||
                    doc.university.contains(keyword, ignoreCase = true)
        }
    }

    fun getDocumentbyUserID(): List<Document> {
return sampleDocuments
    }

}