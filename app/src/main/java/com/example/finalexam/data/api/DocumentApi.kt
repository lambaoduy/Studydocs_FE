package com.example.finalexam.data.api

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.Document
import retrofit2.Response
import retrofit2.http.*

interface DocumentApi {
    @GET("/detail/{documentId}")
    suspend fun getDocumentDetail(@Path("documentId") documentId: String): Response<BaseResponse<Document>>

    @GET("/download/{documentId}")
    suspend fun getDownloadUrl(@Path("documentId") documentId: String): Response<BaseResponse<String>>

    @POST("/{documentId}/like")
    suspend fun likeDocument(@Path("documentId") documentId: String, @Query("userId") userId: String): Response<BaseResponse<Void>>

    @DELETE("/{documentId}/like")
    suspend fun unlikeDocument(@Path("documentId") documentId: String, @Query("userId") userId: String): Response<BaseResponse<Void>>
    @GET("/documents/all")
    suspend fun getAllDocuments(): Response<BaseResponse<List<Document>>>

    @GET("/documents/search")
    suspend fun searchDocuments(@Query("keyword") keyword: String): Response<BaseResponse<List<Document>>>

    @GET("/documents/user")
    suspend fun getDocumentsByUserID(@Query("userId") userId: String): Response<BaseResponse<List<Document>>>

}