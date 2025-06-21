package com.example.finalexam.data.api

import com.example.finalexam.data.dao.document.DocumentListWrapper
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.Document
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DocumentApi {
    @GET("/detail/{documentId}")
    suspend fun getDocumentDetail(@Path("documentId") documentId: String): Response<BaseResponse<Document>>

    @GET("/download/{documentId}")
    suspend fun getDownloadUrl(@Path("documentId") documentId: String): Response<BaseResponse<String>>

    @POST("/{documentId}/like")
    suspend fun likeDocument(@Path("documentId") documentId: String, @Query("userId") userId: String): Response<BaseResponse<Void>>

    @DELETE("/{documentId}/like")
    suspend fun unlikeDocument(@Path("documentId") documentId: String, @Query("userId") userId: String): Response<BaseResponse<Void>>

    @GET("/controller/getAllDocument")
    suspend fun getAllDocuments(): Response<BaseResponse<DocumentListWrapper>>

    @GET("/controller/searchByTitle")
    suspend fun searchDocumentByTitle(
        @Query("keyword") keyword: String
    ): Response<BaseResponse<DocumentListWrapper>>

    @GET("/controller/searchBySubject")
    suspend fun searchDocumentBySubject(
        @Query("keyword") keyword: String
    ): Response<BaseResponse<DocumentListWrapper>>

    @GET("/controller/searchByUniversity")
    suspend fun searchDocumentByUniversity(
        @Query("keyword") keyword: String
    ): Response<BaseResponse<DocumentListWrapper>>

    @GET("/documents/user")
    suspend fun getDocumentsByUserID(
        @Query("userId") userId: String
    ): Response<BaseResponse<DocumentListWrapper>>

}