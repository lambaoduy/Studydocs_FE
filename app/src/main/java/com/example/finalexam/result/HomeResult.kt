package com.example.finalexam.result

import com.example.finalexam.entity.Document

//file này duy viết
sealed class HomeResult {
    object Loading : HomeResult()
    data class LoadByUserID(val data: List<Document>):HomeResult()
    data class Find(val todos: List<Document>): HomeResult()
    data class Error(val throwable: Throwable) : HomeResult()
}