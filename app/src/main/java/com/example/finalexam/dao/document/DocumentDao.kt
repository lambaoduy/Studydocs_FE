package com.example.finalexam.dao.document

import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class DocumentDao(private val api: DocumentApi) {

    suspend fun getAll(): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.getAllDocuments() }
    }

    suspend fun getDocumentsByKeyword(keyword: String): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.searchDocuments(keyword) }
    }

    suspend fun getDocumentbyUserID(userId: String): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.getDocumentsByUserID(userId) }
    }

    // --- Helper function chung ---

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<BaseResponse<List<T>>>): List<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful && response.body()?.stautus == 200) {
                response.body()?.data ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
