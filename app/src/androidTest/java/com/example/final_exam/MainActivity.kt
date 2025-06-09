package com.example.final_exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.final_exam.ui.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEntryPoint()
        }
    }

    @Composable
    fun AppEntryPoint() {
        var currentScreen by remember { mutableStateOf("login") }

        when (currentScreen) {
            "login" -> LoginScreen(
                onLoginClick = { _, _ -> },
                onNavigateToRegister = {
                    currentScreen = "register"
                }
            )
        }
    }

    @Preview(showBackground = true, name = "Login Screen Preview",
        device = "spec:width=1080px,height=2340px,dpi=440"
    )
    @Composable
    fun PreviewAppEntryPoint() {
        AppEntryPoint()
    }
}
