package com.example.finalexam.data.dao.document

import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
//lớp này chưa xong
class DocumentDao(private val api: DocumentApi) {

    suspend fun getAll(): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.getAllDocuments() }
    }

    suspend fun getDocumentsByKeyword(keyword: String): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.searchDocumentByTitle(keyword) }
    }


    suspend fun getDocumentsBySubject(keyword: String): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.searchDocumentBySubject(keyword) }
    }

    suspend fun getDocumentsByUniversity(keyword: String): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.searchDocumentByUniversity(keyword) }
    }

    // --- Helper function chung ---

    private suspend fun safeApiCall(
        apiCall: suspend () -> Response<BaseResponse<DocumentListWrapper>>
    ): List<Document> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == 200) {
                    println("✅ [safeApiCall] Success - dữ liệu: ${body.data?.documents?.size} item")
                    body.data?.documents ?: emptyList()
                } else {
                    println("⚠️ [safeApiCall] API trả về status != 200: ${body?.status}")
                    emptyList()
                }
            } else {
                println("❌ [safeApiCall] API response lỗi: ${response.code()} - ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            println("🔥 [safeApiCall] Exception khi gọi API: ${e.message}")
            emptyList()
        }
    }



}
