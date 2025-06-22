package com.example.finalexam.data.api

import com.example.finalexam.data.dao.document.DocumentListWrapper
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.Document
import retrofit2.Response
import retrofit2.http.*

interface DocumentApi {
    @GET("/document/detail/{documentId}")
    suspend fun getDocumentDetail(@Path("documentId") documentId: String): Response<BaseResponse<Document>>

    @GET("/user/document/download/{documentId}")
    suspend fun getDownloadUrl(@Path("documentId") documentId: String): Response<BaseResponse<String>>

    @POST("/user/document/{documentId}/like")
    suspend fun likeDocument(@Path("documentId") documentId: String): Response<BaseResponse<Boolean>>

    @DELETE("/user/document/{documentId}/like")
    suspend fun unlikeDocument(@Path("documentId") documentId: String): Response<BaseResponse<Boolean>>

    @GET("/document/getAllDocument")
    suspend fun getAllDocuments(): Response<BaseResponse<DocumentListWrapper>>

    @GET("/document/searchByTitle")
    suspend fun searchDocumentByTitle(
        @Query("keyword") keyword: String
    ): Response<BaseResponse<DocumentListWrapper>>

    @GET("/document/searchBySubject")
    suspend fun searchDocumentBySubject(
        @Query("keyword") keyword: String
    ): Response<BaseResponse<DocumentListWrapper>>

    @GET("/document/searchByUniversity")
    suspend fun searchDocumentByUniversity(
        @Query("keyword") keyword: String
    ): Response<BaseResponse<DocumentListWrapper>>
}