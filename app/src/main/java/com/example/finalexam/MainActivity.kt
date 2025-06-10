package com.example.finalexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalexam.ui.myLibraryScreen.MyLibraryScreen
import com.example.finalexam.ui.myLibraryScreen.UploadDocumentScreen
import androidx.compose.runtime.*
import com.example.finalexam.view.LoginScreen
import com.example.finalexam.view.RegisterScreen
import com.example.finalexam.view.ForgotPasswordScreen
import com.example.finalexam.view.HomeScreen
import com.example.finalexam.ui.theme.FinalExamTheme
import com.example.finalexam.view.EditProfileScreen
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinalExamTheme {
                var currentScreen by remember { mutableStateOf("login") }
                var userId by remember { mutableStateOf("") }
                var showLoginRequired by remember { mutableStateOf(false) }

                if (showLoginRequired) {
                    AlertDialog(
                        onDismissRequest = { showLoginRequired = false },
                        title = { Text("Yêu cầu đăng nhập") },
                        text = { Text("Bạn phải đăng nhập để sử dụng chức năng này.") },
                        confirmButton = {
                            Button(onClick = {
                                showLoginRequired = false
                                currentScreen = "login"
                            }) { Text("Đăng nhập") }
                        }
                    )
                }

                when (currentScreen) {
                    "login" -> LoginScreen(
                        onRegisterClick = { currentScreen = "register" },
                        onForgotPasswordClick = { currentScreen = "forgot" }
                    )
                    "register" -> RegisterScreen(
                        onLoginClick = { currentScreen = "login" }
                    )
                    "forgot" -> ForgotPasswordScreen(
                        onBackToLogin = { currentScreen = "login" }
                    )
                    "home" -> HomeScreen()
                    "edit_profile" -> {
                        if (userId.isNotBlank()) {
                            EditProfileScreen(
                                userId = userId,
                                onBack = { currentScreen = "home" }
                            )
                        } else {
                            showLoginRequired = true
                        }
                    }
                }
            }
        }
    }
}

