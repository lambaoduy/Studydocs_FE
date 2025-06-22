// com.example.finalexam.handler.document/DocumentSaveHandler.kt

package com.example.finalexam.handler.document

import com.example.finalexam.data.dao.document.DocumentDao
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult
import com.example.finalexam.domain.usecase.SaveDocumetnUsecase
//import kotlin.Result // <-- RẤT QUAN TRỌNG: Import kotlin.Result ở đây!

class DocumentSaveHandler(private val documentDao: DocumentDao) : IntentHandler<DocumentIntent, DocumentResult> {
    private val documentUseCase = SaveDocumetnUsecase(dao = documentDao)

    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.SaveDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val documentIntent = intent as? DocumentIntent.SaveDocument
            ?: run {
                setResult(DocumentResult.Error("Lỗi: Sai loại Intent được truyền cho DocumentSaveHandler."))
                return
            }

        // Gọi use case và xử lý kết quả
        // Bây giờ .onSuccess và .onFailure sẽ hoạt động
        documentUseCase.invoke(documentIntent.documentId)
            .onSuccess {
                setResult(DocumentResult.OperationSuccess)
            }
            .onFailure { throwable ->
                setResult(DocumentResult.Error("Lưu tài liệu thất bại: ${throwable.message ?: "Lỗi không xác định"}"))
            }
    }
}