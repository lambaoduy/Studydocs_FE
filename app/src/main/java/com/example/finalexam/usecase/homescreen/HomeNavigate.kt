package com.example.finalexam.usecase.homescreen

import com.example.finalexam.entity.Document
import com.example.finalexam.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object HomeNavigate {
    private val _navigation = MutableSharedFlow<NavigationEvent>()
//    val navigation = _navigation.asSharedFlow()

    suspend fun toDocDetail(document: Document) {
        _navigation.emit(NavigationEvent.NavigateToDocumentDetail(document.id))
    }
}
