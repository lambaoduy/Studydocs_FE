package com.example.finalexam.config

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage


object FirebaseConfig {
    lateinit var firebaseAuth: FirebaseAuth
        private set

    lateinit var firebaseStorage: FirebaseStorage
        private set

    fun init(context: Context) {
        // Khởi tạo FirebaseApp nếu chưa có
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context)
        }
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
    }
}