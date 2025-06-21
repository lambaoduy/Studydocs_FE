package com.example.finalexam.reduce

import com.example.finalexam.result.MyLibraryResult
import com.example.finalexam.state.MyLibraryState

class MyLibraryReducer {
    fun reduce(state: MyLibraryState, result: MyLibraryResult): MyLibraryState =
        when (result) {
            is MyLibraryResult.Loading -> 
                state.copy(isLoading = true, error = null)
                
            is MyLibraryResult.LoadDocumentsSuccess -> 
                state.copy(
                    isLoading = false,
                    documents = result.documents,
                    error = null
                )
                
            is MyLibraryResult.SearchSuccess -> 
                state.copy(
                    isLoading = false,
                    documents = result.documents,
                    error = null
                )
                
            is MyLibraryResult.SelectDocumentSuccess -> 
                state.copy(
                    isLoading = false,
                    selectedDocument = result.document,
                    error = null
                )
                
            is MyLibraryResult.Error -> 
                state.copy(
                    isLoading = false,
                    error = result.message
                )
        }
} 