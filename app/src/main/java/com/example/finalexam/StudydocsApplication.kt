package com.example.finalexam

import android.app.Application
import com.example.finalexam.config.FirebaseConfig

class StudydocsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseConfig.init(this)
    }
}