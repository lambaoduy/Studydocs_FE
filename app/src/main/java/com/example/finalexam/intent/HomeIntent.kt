package com.example.finalexam.intent
//file này duy viết
sealed class HomeIntent {
    data class FindTodo(val search: String):HomeIntent()//tìm kiếm
    data class LoadByUserID(val userid: String):HomeIntent()//load dữ liệu theo userID

}