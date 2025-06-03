package com.example.finalexam.usecase.homescreen

import com.example.finalexam.dao.document.DocumentDao
import com.example.finalexam.entity.Document
import com.example.finalexam.result.HomeResult

class HomeLoadDataUseCase {
    var dao= DocumentDao()
    // Trả về document theo userID
    fun loadByUserID(userid: String): List<Document> {
        var listDocument= listOf<Document>()
        /*nếu id là null hoặc rỗng thì lấy tất cả doc có thể truy suất
        * */
        if(userid.isBlank()){
            listDocument=dao.getAll()
        }else{
            listDocument=dao.getDocumentbyUserID()
        }
        return listDocument;
    }
    fun findDocument(keyword: String):List<Document> {
        return dao.getDocumentsByKeyword(keyword);
    }
}