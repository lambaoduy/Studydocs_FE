package com.example.finalexam.reduce

import com.example.finalexam.result.UploadDocumentResult
import com.example.finalexam.state.UploadDocumentState


class UploadDocumentReducer {
    fun reduce(state: UploadDocumentState, result: UploadDocumentResult): UploadDocumentState =
        when (result) {
            is UploadDocumentResult.Loading ->
                state.copy(isUploading = true, error = null)

            is UploadDocumentResult.SetSelectedDocument ->
                state.copy(
                    isUploading = false,
                    selectedDocument = result.document,
                    error = null
                )

            is UploadDocumentResult.SelectUniversitySuccess ->
                state.copy(
                    isUploading = false,
                    university = result.university,
                    error = null
                )

            is UploadDocumentResult.SelectCourseSuccess ->
                state.copy(
                    isUploading = false,
                    university = state.university?.copy(selectedCourseIndex = result.courseIndex),
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

            is UploadDocumentResult.SetTitleSuccess ->
                state.copy(title = result.title)

            is UploadDocumentResult.SetDescriptionSuccess ->
                state.copy(description = result.description)

            is UploadDocumentResult.SubjectListLoaded ->
                state.copy(subjectList = result.subjects)

            is UploadDocumentResult.SubjectCreated ->
                state.copy(
                    subject = result.subject,
                    isCreatingSubject = false
                )
        }
}