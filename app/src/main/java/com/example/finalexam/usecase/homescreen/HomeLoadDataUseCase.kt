

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
        println("Subject keyword: $keyword")
        val filteredList = listDocument?.filter { it.subject.equals(keyword, ignoreCase = true) }
        return if (!filteredList.isNullOrEmpty()) {
            filteredList
        } else {
            dao.getDocumentsBySubject(keyword)
        }
    }
    // logic nếu state ko có danh sách nào thì gọi db, nếu có thì lọc trong danh sách đó
    suspend fun findDocumentBySchool(keyword: String,listDocument: List<Document>?): List<Document> { val filteredList = listDocument?.filter { it.subject.equals(keyword, ignoreCase = true) }
        return if (!filteredList.isNullOrEmpty()) {

            filteredList
        } else {
            dao.getDocumentsBySubject(keyword)
        }
    }

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

        // Nếu không có kết quả từ cache → gọi DB
        result = when {
            !keyword.isNullOrBlank() -> dao.getDocumentsByKeyword(keyword)
            !school.isNullOrBlank() -> dao.getDocumentsByUniversity(school)
            !subject.isNullOrBlank() -> dao.getDocumentsBySubject(subject)
            else -> dao.getAll()
        }

        // Sau khi gọi từ DB theo keyword, lọc tiếp theo school và subject nếu cần
        if (!school.isNullOrBlank()) {
            result = result.filter { it.university.contains(school, ignoreCase = true) }
        }

        if (!subject.isNullOrBlank()) {
            result = result.filter { it.subject.contains(subject, ignoreCase = true) }
        }

        return result
    }



}
