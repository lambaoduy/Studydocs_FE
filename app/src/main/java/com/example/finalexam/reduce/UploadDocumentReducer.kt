package com.example.finalexam.reduce

import com.example.finalexam.result.UploadDocumentResult
import com.example.finalexam.state.UploadDocumentState


class UploadDocumentReducer {
    fun reduce(state: UploadDocumentState, result: UploadDocumentResult): UploadDocumentState =
        when (result) {
            is UploadDocumentResult.Loading ->
                state.copy(isUploading = true, error = null)

            is UploadDocumentResult.DocumentSelected ->
                state.copy(
                    selectedDocument = result.document,
                    error = null
                )

            is UploadDocumentResult.UploadSuccess ->
                state.copy(
                    isUploading = false,
                    uploadSuccess = true,
                    uploadedDocuments = result.uploadedDocuments,
                    error = null
                )

            is UploadDocumentResult.Error ->
                state.copy(
                    isUploading = false,
                    error = result.message
                )

            is UploadDocumentResult.SetTitle ->
                state.copy(title = result.title)

            is UploadDocumentResult.SetDescription ->
                state.copy(description = result.description)
        }
}