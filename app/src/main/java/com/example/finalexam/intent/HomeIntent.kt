package com.example.finalexam.intent

import com.example.finalexam.entity.Document

//file này duy viết
sealed class HomeIntent {

    data class LoadByUserID(val userid: String):HomeIntent()//load dữ liệu theo userID
    object  GetAllTodo: HomeIntent() //lấy tất cả doc trong cơ sở dữ liệu
    data class  NavigateToDocDetail(val document:Document):HomeIntent() //xem chi tiết doc
    data class FindWithFilters(
        val keyword: String? = null,
        val school: String? = null,
        val subject: String? = null
    ) : HomeIntent()
}