package com.example.finalexam.result

import com.example.finalexam.entity.Document

sealed class MyLibraryResult {
    data object Loading : MyLibraryResult()
    data class LoadDocumentsSuccess(val documents: List<Document>,val documentsSave: List<Document>) : MyLibraryResult()
    data class SearchSuccess(val documents: List<Document>) : MyLibraryResult()
    data class SelectDocumentSuccess(val document: Document) : MyLibraryResult()
    data class Error(val message: String) : MyLibraryResult()
}