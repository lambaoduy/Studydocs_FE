package com.example.finalexam.api

import com.example.finalexam.entity.Document
import retrofit2.Response
import retrofit2.http.*

interface DocumentApi {
    @GET("/documents/detail/{documentId}")
    suspend fun getDocumentDetail(@PathVariable("documentId") documentId: String): Response<Document>

    @GET("/documents/download/{documentId}")
    suspend fun getDownloadUrl(@PathVariable("documentId") documentId: String): Response<String>

    @POST("/documents/{documentId}/like")
    suspend fun likeDocument(@PathVariable("documentId") documentId: String, @Query("userId") userId: String): Response<Void>

    @DELETE("/documents/{documentId}/like")
    suspend fun unlikeDocument(@PathVariable("documentId") documentId: String, @Query("userId") userId: String): Response<Void>

    @POST("/documents/{documentId}/bookmark")
    suspend fun bookmarkDocument(@PathVariable("documentId") documentId: String, @Query("userId") userId: String): Response<Void>

    @DELETE("/documents/{documentId}/bookmark")
    suspend fun unbookmarkDocument(@PathVariable("documentId") documentId: String, @Query("userId") userId: String): Response<Void>
}

object RetrofitClient {
    private const val BASE_URL = "http://your-backend-url:8080/" // Thay bằng URL backend của bạn
    val api: DocumentApi by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(DocumentApi::class.java)
    }
}