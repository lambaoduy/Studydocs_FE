package com.example.finalexam.data.api

import com.example.finalexam.data.dao.document.DocumentListWrapper
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.Document
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


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

    @GET("/documents/user")
    suspend fun getDocumentsByUserID(
        @Query("userId") userId: String
    ): Response<BaseResponse<DocumentListWrapper>>


    //===Phần này của Hảo===
    @Multipart
    @POST("/user/document/upload")
    suspend fun uploadDocument(@Part("document") document: RequestBody, @Part file: MultipartBody.Part): Response<BaseResponse<Document>>
    //===Phần này của Hảo end===

    //===Phần này của Hảo end===
    @GET("/user/document/my-documents")
    suspend fun getMyDocuments(): Response<BaseResponse<DocumentListWrapper>>
    //===Phần này của Hảo end===
}