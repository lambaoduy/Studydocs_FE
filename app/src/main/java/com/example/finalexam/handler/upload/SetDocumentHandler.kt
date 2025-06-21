package com.example.finalexam.handler.upload

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.result.UploadDocumentResult

class SetDocumentHandler : IntentHandler<UploadDocumentIntent, UploadDocumentResult> {

    override fun canHandle(intent: UploadDocumentIntent): Boolean = 
        intent is UploadDocumentIntent.SetSelectedDocument || intent is UploadDocumentIntent.RemoveSelectedDocument

    override suspend fun handle(
        intent: UploadDocumentIntent,
        setResult: (UploadDocumentResult) -> Unit
    ) {
        when (intent) {
            is UploadDocumentIntent.SetSelectedDocument -> {
                setResult(UploadDocumentResult.DocumentSelected(intent.document))
            }
            is UploadDocumentIntent.RemoveSelectedDocument -> {
                setResult(UploadDocumentResult.DocumentSelected(null))
            }
            else -> {
                setResult(UploadDocumentResult.Error("Invalid intent"))
            }
        }
    }
} 