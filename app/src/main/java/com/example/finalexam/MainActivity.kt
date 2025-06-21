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
import com.example.finalexam.ui.screens.ForgotPasswordScreen
import com.example.finalexam.ui.screens.HomeScreen
import com.example.finalexam.ui.screens.LoginScreen
import com.example.finalexam.ui.screens.NotificationScreen
import com.example.finalexam.ui.screens.ProfileScreen
import com.example.finalexam.ui.screens.RegisterScreen
import com.example.finalexam.ui.theme.FinalExamTheme
import com.example.finalexam.view.myLibraryScreen.MyLibraryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
            FinalExamTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            onRegisterClick = { navController.navigate("register") },
                            onForgotPasswordClick = { navController.navigate("forgot_password") },
                            onLoginSuccess = { navController.navigate("home") },

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
                            onNavigateToDocumentDetail = { docId ->
                                navController.navigate("document_detail/$docId")
                            },
                            onNavigateToRoute = {
                                navController.navigate("profile") // 👈 điều hướng tới Profile
                            }
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
                    composable("library") { /* LibraryScreen() */
                        MyLibraryScreen()
                    }
                }
            }
        }

    }
}

