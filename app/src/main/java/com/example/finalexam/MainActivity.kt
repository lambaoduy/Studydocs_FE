package com.example.finalexam


import FollowScreen
import ProfileScreen
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalexam.data.datastore.UserPreferences
import com.example.finalexam.data.datastore.UserProvider
import com.example.finalexam.ui.document.DocumentDetailScreen
import com.example.finalexam.ui.screens.EditProfileScreen
import com.example.finalexam.ui.screens.ForgotPasswordScreen
import com.example.finalexam.ui.screens.HomeScreen
import com.example.finalexam.ui.screens.LoginScreen
import com.example.finalexam.ui.screens.NotificationScreen
import com.example.finalexam.ui.screens.RegisterScreen
import com.example.finalexam.ui.screens.myLibraryScreen.MyLibraryScreen
import com.example.finalexam.ui.screens.myLibraryScreen.UploadDocumentScreen
import com.example.finalexam.ui.theme.FinalExamTheme
import kotlinx.coroutines.flow.firstOrNull

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermissionIfNeeded()
        enableEdgeToEdge()
        setContent {
            FinalExamTheme {
                val navController = rememberNavController()
                val startDestination by produceState<String>("login") {
                    val userId = UserPreferences.getUserId().firstOrNull()
                    val user = UserPreferences.getUser()
                    value = if (userId != null && user != null) "home" else "login"
                }
                val user by produceState<com.example.finalexam.entity.User?>(null) {
                    value = UserPreferences.getUser()
                }
                NavHost(navController = navController, startDestination = "login") {
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
                            onBottomNavItemSelected = { route ->
                                navController.navigate(route)
                            },
                            navigateToProfile = { navController.navigate("profile") },
                            navigateToLogin = { navController.navigate("login") },
                            navigateToRegister = { navController.navigate("register") },
                            user = user
                        )
                    }
                    composable("profile") {
                        ProfileScreen(
                            onEditProfile = { navController.navigate("edit-profile") },
                            followSetting = { navController.navigate("follow") },
                            onLibrary = { navController.navigate("library") },
                            onLogout = { navController.navigate("login") },
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable("edit-profile") {
                        EditProfileScreen(
                            onBackClick = {
                                navController.popBackStack()
                            },
                            userId = UserProvider.getUserId(),
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
                    composable("library") {
                        MyLibraryScreen(
                            onNavigateToUpload = { navController.navigate("upload") },
                            onNavigateToDocumentDetail = { docId ->
                                navController.navigate("document_detail/$docId")
                            },
                            onNavigateToHome = { navController.navigate("home") }
                        )
                    }
                    composable("upload") {
                        UploadDocumentScreen(
                            onBackClick = { navController.popBackStack() },
                            onUploadSuccess = {navController.navigate("library")}
                        )
                    }
                }
            }
        }

    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }

}

