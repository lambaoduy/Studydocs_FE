package com.example.finalexam.intent
//file này duy viết
sealed class HomeIntent {
    data class FindTodo(val search: String):HomeIntent()//tìm kiếm
    data class LoadByUserID(val userid: String):HomeIntent()//load dữ liệu theo userID
    data class FindTodoBySchool(val school: String) : HomeIntent() //tìm kiếm theo school
    data class FindTodoBySubject(val subject: String) : HomeIntent()//tìm kiếm theo subject

}