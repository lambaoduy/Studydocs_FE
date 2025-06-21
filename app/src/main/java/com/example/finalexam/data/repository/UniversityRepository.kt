package com.example.finalexam.data.repository

import com.example.finalexam.data.api.UniversityApi
import com.example.finalexam.entity.University
import com.example.finalexam.network.RetrofitClient

class UniversityRepository() {
    private val universityApi: UniversityApi = RetrofitClient.createApi(UniversityApi::class.java)
    suspend fun getAllUniversities(): List<University> {
        val response = universityApi.getAllUniversities()
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception(response.body()?.message ?: "Lỗi lấy danh sách trường")
        }
    }

    suspend fun addSubject(universityId: String, subject: String): Boolean {
        val response = universityApi.addSubject(universityId, subject)
        if (response.isSuccessful) {
            return response.body()?.data == true
        } else {
            throw Exception(response.body()?.message ?: "Lỗi thêm môn học")
        }
    }
}