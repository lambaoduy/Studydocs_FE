package com.example.finalexam.usecase.homescreen

import com.example.finalexam.entity.Document
import com.example.finalexam.result.HomeResult

class HomeLoadDataUseCase {
    // Danh sách document mẫu
    private val sampleDocuments = listOf(
        Document("1", "Lập trình Kotlin", "Kỹ thuật phần mềm"),
        Document("2", "Cấu trúc dữ liệu", "Khoa học máy tính"),
        Document("3", "Toán rời rạc", "Toán học"),
    )

    // Trả về document theo userID
    fun loadByUserID(userid: String): List<Document> {
       return sampleDocuments;
    }
}