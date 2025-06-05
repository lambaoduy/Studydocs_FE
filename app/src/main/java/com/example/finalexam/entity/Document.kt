package com.example.finalexam.entity

data class Document(
    val id: String,
    val title: String,
      var subject: String,
    var university: String,
    val author: String,
    val createdDate: String

) {
    val shortTitle: String
        get() = if (title.length > 10) title.take(10) + "..." else title
}

