package com.example.finalexam.usecase.upload

import android.content.Context
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.data.datastore.UserPreferences
import com.example.finalexam.data.utils.FileUtil
import com.example.finalexam.entity.Document
import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.result.UploadDocumentResult
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.wait
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

    private val documentApi: DocumentApi = RetrofitClient.createApi(DocumentApi::class.java)
    private val gson = Gson()

    suspend operator fun invoke(
        documents: List<UploadDocument>,
        universityId: String,
        universityName: String,
        courseIndex: Int,
        context: Context
    ): UploadDocumentResult {
        return try {
            if (documents.isEmpty()) {
                return UploadDocumentResult.Error("Không có documents nào để upload")
            }

            if (universityId.isBlank()) {
                return UploadDocumentResult.Error("University ID không hợp lệ")
            }

            if (courseIndex < 0) {
                return UploadDocumentResult.Error("Course index không hợp lệ")
            }

            val uploadedDocuments = mutableListOf<Document>()

            for (uploadDoc in documents) {
                try {
                    if (uploadDoc.uri == null) {
                        return UploadDocumentResult.Error("File không hợp lệ: ${uploadDoc.name}")
                    }

                    val document = UserPreferences.getUser()?.let {
                        Document(
                            title = uploadDoc.title,
                            description = uploadDoc.description,
                            fileUrl = uploadDoc.fileUrl,
                            university = universityName,
                            subject = uploadDoc.subject,
                            author = it.fullName,
                            createdDate = System.currentTimeMillis().toString()
                        )
                    } ?: return UploadDocumentResult.Error("Không tìm thấy thông tin người dùng")

                    val tempFile = FileUtil.getFileFromUri(context, uploadDoc.uri, uploadDoc.name)

                    val filePart = createFilePart(tempFile)
                    val documentJson = gson.toJson(document)
                    val documentBody = documentJson.toRequestBody("application/json".toMediaType())

                    val response = documentApi.uploadDocument(documentBody, filePart)

//                    if (response.isSuccessful) {
//                        response.body()?.data?.let { uploadedDoc ->
//                            uploadedDocuments.add(uploadedDoc)
//                        }
//                    } else {
//                        return UploadDocumentResult.Error("Upload failed: ${response.message()}")
//                    }

                    // Safe delete file
                    try {
                        tempFile.delete()
                    } catch (e: Exception) {
                        println("Không thể xóa file tạm: ${e.message}")
                    }

                } catch (e: Exception) {
                    return UploadDocumentResult.Error("Error uploading ${uploadDoc.name}: ${e.message}")
                }
            }

            //
            return UploadDocumentResult.UploadSuccess(uploadedDocuments)

        } catch (e: Exception) {
            println("UploadDocumentsUseCase error: ${e.message}")
            UploadDocumentResult.Error(e.message ?: "Failed to upload documents")
        }
    }

    private fun createFilePart(file: File): MultipartBody.Part {
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestBody)
    }
}