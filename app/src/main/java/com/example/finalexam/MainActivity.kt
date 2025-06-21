package com.example.finalexam


import FollowScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalexam.data.datastore.UserProvider
import com.example.finalexam.ui.document.DocumentDetailScreen
import com.example.finalexam.ui.screens.ForgotPasswordScreen
import com.example.finalexam.ui.screens.HomeScreen
import com.example.finalexam.ui.screens.LoginScreen
import com.example.finalexam.ui.screens.NotificationScreen
import com.example.finalexam.ui.screens.ProfileScreen
import com.example.finalexam.ui.screens.RegisterScreen
//===Phần này của Hảo 22/6===
import com.example.finalexam.ui.screens.myLibraryScreen.MyLibraryScreen
import com.example.finalexam.ui.screens.myLibraryScreen.UploadDocumentScreen
//===Phần này của Hảo 22/6===
import com.example.finalexam.ui.theme.FinalExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinalExamTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("login") {
                        LoginScreen(
                            onRegisterClick = { navController.navigate("register") },
                            onForgotPasswordClick = { navController.navigate("forgot_password") },
                            onLoginSuccess = { navController.navigate("home") }
                        )
                    }
                    composable("register") {
                        RegisterScreen(
                            onLoginClick = {
                                navController.popBackStack(
                                    "login",
                                    inclusive = false
                                )
                            },
                            onRegisterSuccess = {
                                navController.popBackStack(
                                    "login",
                                    inclusive = false
                                )
                            }
                        )
                    }
                    composable("forgot_password") {
                        ForgotPasswordScreen(
                            onBackClick = {
                                navController.popBackStack(
                                    "login",
                                    inclusive = false
                                )
                            },
                            onSuccess = { navController.popBackStack("login", inclusive = false) }
                        )
                    }
                    composable("home") {
                        HomeScreen(
                            navigateToNotification = { navController.navigate("notification") },
                            onNavigateToDocumentDetail = { docId ->
                                navController.navigate("document_detail/$docId")
                            },
                            //===Phần này của Hảo 22/6===
                            onBottomNavItemSelected = { route ->
                                when (route) {
                                    "library" -> navController.navigate("mylibrary")
                                    "home" -> navController.navigate("home")
                                    "acount" -> navController.navigate("profile")
                                    else -> navController.navigate(route)
                                }
                            }
                            //===Phần này của Hảo 22/6===
                        )
                    }
                    composable("profile") {
                        ProfileScreen(
                            userId = UserProvider.getUserId(),
                            onEditProfile = { navController.navigate("edit_profile") }
                        )
                    }
                    composable("notification") {
                        NotificationScreen(
                            navigateToFollow = { navController.navigate("follow") },
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable("follow") {
                        FollowScreen(
                            userId = UserProvider.getUserId(),
                            onBackClick = { navController.popBackStack() })
                    }
                    composable("document_detail/{documentId}") {
                        DocumentDetailScreen(
                            documentId = it.arguments?.getString("documentId") ?: "",
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    //===Phần này của Hảo 22/6===
                    composable("mylibrary") {
                        MyLibraryScreen(
                            onNavigateToUpload = { navController.navigate("upload_document") },
                            onNavigateToDocumentDetail = { docId ->
                                navController.navigate("document_detail/$docId")
                            },
                            onNavigateToHome = { navController.navigate("home") }
                        )
                    }
                    composable("upload_document") {
                        UploadDocumentScreen(
                            onBackClick = { navController.popBackStack() },
                            onUploadClick = { document ->
                                // Có thể thêm logic xử lý khi click upload
                            },
                            onUploadSuccess = {
                                // Chuyển về MyLibrary sau khi upload thành công
                                navController.navigate("mylibrary") {
                                    popUpTo("upload_document") { inclusive = true }
                                }
                            }
                        )
                    }
                    //===Phần này của Hảo 22/6===
                }
            }
        }

    }
}

