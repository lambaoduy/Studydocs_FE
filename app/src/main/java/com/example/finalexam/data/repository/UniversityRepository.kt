package com.example.finalexam.data.repository

import com.example.finalexam.data.api.UniversityApi
import com.example.finalexam.entity.University

class UniversityRepository(private val api: UniversityApi) {

    suspend fun getAllUniversities(): List<University> {
        val response = api.getAllUniversities()
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception(response.body()?.message ?: "Lỗi lấy danh sách trường")
        }
    }

    suspend fun addSubject(universityId: String, subject: String): Boolean {
        val response = api.addSubject(universityId, subject)
        if (response.isSuccessful) {
            return response.body()?.data == true
        } else {
            throw Exception(response.body()?.message ?: "Lỗi thêm môn học")
        }
    }
}