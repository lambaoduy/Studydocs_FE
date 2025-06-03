package com.example.finalexam.result

import com.example.finalexam.entity.Document

//file này duy viết
sealed class HomeResult {
    object Loading : HomeResult()
    data class LoadByUserID(val data: List<Document>):HomeResult()//danh sách documents theo userid
    data class Find(val data: List<Document>): HomeResult()//tìm kiếm
    data class Error(val throwable: Throwable) : HomeResult()// lỗi
}