//package com.example.finalexam.ui.location
//
//import com.example.finalexam.data.api.UniversityApi
//import com.example.finalexam.data.repository.UniversityRepository
//import com.example.finalexam.usecase.university.AddSubjectUseCase
//import com.example.finalexam.usecase.university.GetAllUniversitiesUseCase
//import com.example.finalexam.usecase.upload.UploadDocumentsUseCase
//import com.example.finalexam.network.RetrofitClient
//
//object ServiceLocator {
//    val universityApi: UniversityApi by lazy {
//        RetrofitClient.createApi(UniversityApi::class.java)
//    }
//
//    val universityRepository: UniversityRepository by lazy {
//        UniversityRepository(universityApi)
//    }
//
//    val getAllUniversitiesUseCase by lazy {
//        GetAllUniversitiesUseCase(universityRepository)
//    }
//
//    val addSubjectUseCase by lazy {
//        AddSubjectUseCase(universityRepository)
//    }
//
//    val uploadDocumentsUseCase by lazy {
//        UploadDocumentsUseCase()
//    }
//}
