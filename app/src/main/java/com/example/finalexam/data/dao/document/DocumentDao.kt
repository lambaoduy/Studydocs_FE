package com.example.finalexam.data.dao.document

import androidx.room.Query
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

    suspend fun getDocumentbyUserID(userId: String): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.getDocumentsByUserID(userId) }
    }

    suspend fun getDocumentsBySubject(keyword: String): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.searchDocumentBySubject(keyword) }
    }

    suspend fun getDocumentsByUniversity(keyword: String): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.searchDocumentByUniversity(keyword) }
    }
    // Thêm method lấy tài liệu của tôi
    suspend fun getMyDocuments(): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.getMyDocuments() }
    }
    // Thêm method lấy tài liệu đã lưu
    suspend fun getSaveDocumests(): List<Document> = withContext(Dispatchers.IO) {
        safeApiCall { api.getSaveDocument() }
    }
    // Hàm để lưu tài liệu, trả về Boolean cho biết thành công hay thất bại
    // hoặc String nếu API trả về một thông báo.
    suspend fun saveDocument(documentId: String): Boolean = withContext(Dispatchers.IO) { // Thay đổi kiểu trả về
        try {
            val response = api.saveDocument(documentId) // Gọi hàm API
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == 200) {
                    println("✅ [DocumentDao] Lưu tài liệu thành công: $documentId - ${body.data}")
                    true // Trả về true nếu thành công
                } else {
                    println("⚠️ [DocumentDao] API trả về status != 200 khi lưu: ${body?.status} - ${body?.message}")
                    false // Trả về false nếu API báo lỗi
                }
            } else {
                println("❌ [DocumentDao] API response lỗi khi lưu: ${response.code()} - ${response.errorBody()?.string()}")
                false // Trả về false nếu phản hồi HTTP lỗi
            }
        } catch (e: Exception) {
            println("🔥 [DocumentDao] Exception khi gọi API lưu tài liệu: ${e.message}")
            false // Trả về false nếu có ngoại lệ
        }
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
