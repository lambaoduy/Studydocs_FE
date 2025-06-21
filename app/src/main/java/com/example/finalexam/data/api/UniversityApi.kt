package com.example.finalexam.data.api

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.University
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UniversityApi {
    /**
     * Lấy danh sách tất cả các trường đại học từ server.
     * Endpoint này tương ứng với API: GET /university/all
     */
    @GET("university/all")
    suspend fun getAllUniversities(): Response<BaseResponse<List<University>>>

    /**
     * Thêm một môn học vào trường đại học
     * @param id: id của trường đại học
     * @param subject: tên môn học (dạng String)
     * Endpoint: POST /university/{id}/subject
     */
    @POST("university/{id}/subject")
    suspend fun addSubject(
        @Path("id") id: String,
        @Body subject: String
    ): Response<BaseResponse<Boolean>>
}