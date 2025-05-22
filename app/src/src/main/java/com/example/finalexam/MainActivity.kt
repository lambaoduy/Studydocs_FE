package com.example.finalexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.finalexam.ui.theme.FinalExamTheme
import com.example.finalexam.view.DocumentDetailScreen
import com.example.finalexam.view.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalExamTheme {
                // Ví dụ gọi DocumentDetailScreen với documentId
                DocumentDetailScreen(documentId = "doc456")
                // Hoặc giữ HomeScreen nếu muốn: HomeScreen()
            }
        }
    }
}