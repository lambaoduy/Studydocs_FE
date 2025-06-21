package com.example.finalexam

import android.app.Application
import com.example.finalexam.config.FirebaseConfig
import com.example.finalexam.data.datastore.UserPreferences
import com.example.finalexam.network.RetrofitClient

class StudydocsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseConfig.init(this)
        UserPreferences.init(this)
        RetrofitClient.init()
    }
}