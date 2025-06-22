// com.example.finalexam.domain.usecase/SaveDocumetnUsecase.kt

package com.example.finalexam.domain.usecase

import com.example.finalexam.data.dao.document.DocumentDao


class SaveDocumetnUsecase(private val dao: DocumentDao) {

    // Usecase trả về kotlin.Result<Unit>
    suspend fun invoke(documentId: String): kotlin.Result<Unit> {
        return try {
            val isSuccess = dao.saveDocument(documentId)
            if (isSuccess) {
                kotlin.Result.success(Unit) // Sử dụng hàm .success() của kotlin.Result
            } else {
                kotlin.Result.failure(Exception("Lưu tài liệu thất bại từ DAO")) // Sử dụng hàm .failure() của kotlin.Result
            }
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
    }
}