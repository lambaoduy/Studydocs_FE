package com.example.finalexam.handler.upload

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.result.UploadDocumentResult

class SetTextHandler : IntentHandler<UploadDocumentIntent, UploadDocumentResult> {

    override fun canHandle(intent: UploadDocumentIntent): Boolean = 
        intent is UploadDocumentIntent.SetTitle || intent is UploadDocumentIntent.SetDescription

    override suspend fun handle(
        intent: UploadDocumentIntent,
        setResult: (UploadDocumentResult) -> Unit
    ) {
        when (intent) {
            is UploadDocumentIntent.SetTitle -> {
                setResult(UploadDocumentResult.SetTitle(intent.title))
            }
            is UploadDocumentIntent.SetDescription -> {
                setResult(UploadDocumentResult.SetDescription(intent.description))
            }
            else -> {
                setResult(UploadDocumentResult.Error("Invalid intent"))
            }
        }
    }
} 