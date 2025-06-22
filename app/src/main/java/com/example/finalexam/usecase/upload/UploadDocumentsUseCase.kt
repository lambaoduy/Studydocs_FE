package com.example.finalexam.usecase.upload

import android.content.Context
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.entity.Document
import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.result.UploadDocumentResult
import com.example.finalexam.data.utils.FileUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * UseCase chịu trách nhiệm thực hiện logic upload documents
 * 
 * Luồng hoạt động:
 * 1. Nhận danh sách documents, universityId và courseIndex
 * 2. Validate dữ liệu đầu vào
 * 3. Gọi API service để upload documents
 * 4. Xử lý response và trả về UploadDocumentResult
 * 
 * Dependencies:
 * - DocumentApi: Để gọi API upload
 * - FileUploadService: Để upload file lên storage
 * 
 * TODO: Cần implement:
 * - Tích hợp với DocumentApi thực tế
 * - Upload file lên Firebase Storage hoặc server
 * - Xử lý progress tracking
 * - Retry mechanism cho upload failed
 */
class UploadDocumentsUseCase {
    
    //===Phần này của Hảo 22/6===
    private val documentApi: DocumentApi = RetrofitClient.createApi(DocumentApi::class.java)
    //===Phần này của Hảo 22/6===
    
    /**
     * Thực hiện upload documents
     * 
     * @param documents Danh sách documents cần upload
     * @param universityId ID của trường đại học
     * @param courseIndex Index của môn học trong danh sách subjects
     * @param context Context để truy cập ContentResolver
     * @return UploadDocumentResult chứa kết quả upload
     */
    suspend operator fun invoke(
        documents: List<UploadDocument>,
        universityId: String,
        courseIndex: Int,
        context: Context
    ): UploadDocumentResult {
        return try {
            // Validate input parameters
            if (documents.isEmpty()) {
                return UploadDocumentResult.Error("Không có documents nào để upload")
            }
            
            if (universityId.isBlank()) {
                return UploadDocumentResult.Error("University ID không hợp lệ")
            }
            
            if (courseIndex < 0) {
                return UploadDocumentResult.Error("Course index không hợp lệ")
            }
            
            //===Phần này của Hảo 22/6===
            // Gọi API thực tế để upload documents
            val uploadedDocuments = mutableListOf<Document>()
            
            for (uploadDoc in documents) {
                try {
                    // Kiểm tra có Uri không
                    if (uploadDoc.uri == null) {
                        return UploadDocumentResult.Error("File không hợp lệ: ${uploadDoc.name}")
                    }
                    
                    // Convert UploadDocument to Document entity
                    val document = Document(
                        title = uploadDoc.title,
                        description = uploadDoc.description,
                        fileUrl = uploadDoc.fileUrl,
                        university = universityId,
                        subject = uploadDoc.subject,
                        author = "current_user", // TODO: Lấy từ UserPreferences
                        createdDate = System.currentTimeMillis().toString()
                    )
                    
                    // Convert Uri to File sử dụng FileUtil
                    val tempFile = FileUtil.getFileFromUri(context, uploadDoc.uri, uploadDoc.name)
                    
                    // Convert file to MultipartBody.Part
                    val filePart = createFilePart(tempFile)
                    
                    // Gọi API upload
                    val response = documentApi.uploadDocument(document, filePart)
                    
                    if (response.isSuccessful) {
                        response.body()?.data?.let { uploadedDoc ->
                            uploadedDocuments.add(uploadedDoc)
                        }
                    } else {
                        return UploadDocumentResult.Error("Upload failed: ${response.message()}")
                    }
                    
                    // Xóa file tạm sau khi upload
                    tempFile.delete()
                    
                } catch (e: Exception) {
                    return UploadDocumentResult.Error("Error uploading ${uploadDoc.name}: ${e.message}")
                }
            }
            
            // Trả về kết quả thành công
            return UploadDocumentResult.UploadSuccess(documents)
            //===Phần này của Hảo 22/6===
            
        } catch (e: Exception) {
            // Log error for debugging
            println("UploadDocumentsUseCase error: ${e.message}")
            
            // Return error result with meaningful message
            UploadDocumentResult.Error(e.message ?: "Failed to upload documents")
        }
    }
    
    //===Phần này của Hảo 22/6===
    /**
     * Convert File to MultipartBody.Part for API upload
     */
    private fun createFilePart(file: File): MultipartBody.Part {
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestBody)
    }
    //===Phần này của Hảo 22/6===
} 