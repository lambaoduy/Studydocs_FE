package com.example.finalexam

import FollowScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
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
import com.example.finalexam.ui.theme.FinalExamTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Đăng xuất người dùng khi khởi động ứng dụng
        FirebaseAuth.getInstance().signOut()
        UserProvider.setUserId("") // Xóa userId khỏi UserProvider
        setContent {
            FinalExamTheme {
                MainNavHost()
            }
        }
    }
}

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onForgotPasswordClick = { navController.navigate("forgot_password") },
                onLoginSuccess = {
                    auth.currentUser?.let { user ->
                        UserProvider.setUserId(user.uid)
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onLoginClick = { navController.popBackStack("login", inclusive = false) },
                onRegisterSuccess = {
                    auth.currentUser?.let { user ->
                        UserProvider.setUserId(user.uid)
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack("login", inclusive = false) },
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
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
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
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("document_detail/{documentId}") {
            DocumentDetailScreen(
                documentId = it.arguments?.getString("documentId") ?: "",
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}