

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
    suspend fun getAll(): List<Document> {
        return dao.getAll() // lấy tất cả
    }
    suspend fun findDocument(keyword: String): List<Document> {
        return if (keyword.isBlank()) {
            dao.getAll()
        } else {
            dao.getDocumentsByKeyword(keyword)
        }
    }
// logic nếu state ko có danh sách nào thì gọi db, nếu có thì lọc trong danh sách đó
    suspend fun findDocumentBySubject(keyword: String, listDocument: List<Document>?): List<Document> {
        return if (listDocument.isNullOrEmpty()) {
            dao.getDocumentsBySubject(keyword)
        } else {
            listDocument.filter { it.subject.equals(keyword, ignoreCase = true)}
        }
    }
    // logic nếu state ko có danh sách nào thì gọi db, nếu có thì lọc trong danh sách đó
    suspend fun findDocumentBySchool(keyword: String,listDocument: List<Document>?): List<Document> {
        return if (listDocument.isNullOrEmpty()) {
            dao.getDocumentsByUniversity(keyword)
        } else {
            listDocument.filter { it.university.equals(keyword, ignoreCase = true)}
        }
    }
}
