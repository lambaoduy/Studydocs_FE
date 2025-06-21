package com.example.finalexam.handler.document

import com.example.finalexam.config.FirebaseConfig
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.result.DocumentResult
import kotlinx.coroutines.tasks.await

class LoadDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    private val firebaseStorage = FirebaseConfig.firebaseStorage
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.LoadDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val loadIntent = intent as DocumentIntent.LoadDocument
        setResult(DocumentResult.Loading)
        try {
            val api = RetrofitClient.createApi(DocumentApi::class.java)
            val response = api.getDocumentDetail(loadIntent.documentId)
            if (response.isSuccessful) {
                val document = response.body()?.data
                if (document != null) {
                    val finalDoc = if (document.fileUrl.startsWith("gs://")) {
                        // Lấy download URL từ Firebase nếu fileUrl là storage URL
                        val ref = firebaseStorage.getReferenceFromUrl(document.fileUrl)
                        val downloadUrl = ref.downloadUrl.await().toString()
                        document.copy(fileUrl = downloadUrl) // cập nhật URL
                    } else {
                        document // dùng luôn URL hiện có
                    }

                    setResult(DocumentResult.Loaded(finalDoc))
                } else {
                    setResult(DocumentResult.Error("Document not found"))
                }
            } else {
                setResult(DocumentResult.Error("Failed to load document: ${response.body()?.message ?: "Unknown"}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error(e.message ?: "Unknown error"))
        }
    }
}