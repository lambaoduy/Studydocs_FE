

/*lớp phụ trách lấy dữ liệu cho trang home
* */
package com.example.finalexam.usecase.homescreen

import com.example.finalexam.data.dao.document.DocumentDao
import com.example.finalexam.entity.Document

class HomeLoadDataUseCase(
    private val dao: DocumentDao // truyền dao từ bên ngoài
) {
    // Sử dụng suspend vì gọi API là bất đồng bộ
    suspend fun loadByUserID(userid: String): List<Document> {
        return if (userid.isBlank()) {
            dao.getAll() // lấy tất cả nếu không có user ID
        } else {
            dao.getDocumentbyUserID(userid)
        }
    }

    suspend fun findDocument(keyword: String): List<Document> {
        return if (keyword.isBlank()) {
            dao.getAll()
        } else {
            dao.getDocumentsByKeyword(keyword)
        }
    }

    suspend fun findDocumentBySubject(keyword: String): List<Document> {
        return if (keyword.isBlank()) {
            dao.getAll()
        } else {
            dao.getDocumentsBy(keyword)
        }
    }

    suspend fun findDocumentBySchool(keyword: String): List<Document> {
        return if (keyword.isBlank()) {
            dao.getAll()
        } else {
            dao.getDocumentsBySchool(keyword)
        }
    }
}
