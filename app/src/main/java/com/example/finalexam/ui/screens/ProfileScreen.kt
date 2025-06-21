package com.example.finalexam.ui.screens

import FollowScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.viewmodel.UpdateProfileViewModel
import com.example.finalexam.entity.User

@Composable
fun ProfileScreen(
    userId: String,
    updateProfileViewModel: UpdateProfileViewModel = viewModel(),
    onEditProfile: () -> Unit = {},
) {
    // Lấy state user từ ViewModel
    val state by updateProfileViewModel.state.collectAsState()
    val user = state.user ?: User(userId = userId)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Thông tin user
        Text(text = "Tên: ${user.username}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Email: ${user.email}")
        user.bio?.let { Text(text = "Bio: $it") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onEditProfile) {
            Text("Chỉnh sửa hồ sơ")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Nhúng FollowScreen
        FollowScreen(userId = userId)
    }
}

