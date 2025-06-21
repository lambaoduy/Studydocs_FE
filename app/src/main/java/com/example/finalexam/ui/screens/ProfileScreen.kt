package com.example.finalexam.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.User
import com.example.finalexam.viewmodel.UpdateProfileViewModel

@Composable
fun ProfileScreen(
    userId: String?,
    updateProfileViewModel: UpdateProfileViewModel = viewModel(),
    onEditProfile: () -> Unit = {},
) {
    // Lấy state user từ ViewModel
    val state by updateProfileViewModel.state.collectAsState()
    val user = state.user ?: User(userId = userId?: "")

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
    }
}

