package com.example.finalexam.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.finalexam.intent.UpdateProfileIntent
import com.example.finalexam.viewmodel.UpdateProfileViewModel
import com.example.finalexam.entity.User
import com.example.finalexam.ui.theme.*
import com.example.finalexam.view.RoundedInputField

@Composable
fun EditProfileScreen(
    userId: String,
    updateProfileViewModel: UpdateProfileViewModel = viewModel(),
    onBack: () -> Unit = {}
) {
    val state by updateProfileViewModel.state.collectAsState()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var avatarUrl by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var initialized by remember { mutableStateOf(false) }

    // Lấy profile khi vào màn hình
    LaunchedEffect(userId) {
        updateProfileViewModel.processIntent(UpdateProfileIntent.Load(userId))
    }
    // Khi có dữ liệu user thì gán vào form
    LaunchedEffect(state.user) {
        state.user?.let {
            if (!initialized) {
                username = it.username
                email = it.email
                avatarUrl = it.avatarUrl ?: ""
                phone = it.phone ?: ""
                bio = it.bio ?: ""
                initialized = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Purple40, PurpleGrey40, creamy)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            if (avatarUrl.isNotBlank()) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Avatar",
                    tint = Purple40,
                    modifier = Modifier.size(96.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            RoundedInputField(
                icon = Icons.Default.AccountCircle,
                placeholder = "Username",
                value = username,
                onValueChange = { username = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedInputField(
                icon = Icons.Default.AccountCircle,
                placeholder = "Email",
                value = email,
                onValueChange = { email = it },
                visualTransformation = VisualTransformation.None
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedInputField(
                icon = Icons.Default.AccountCircle,
                placeholder = "Avatar URL",
                value = avatarUrl,
                onValueChange = { avatarUrl = it },
                visualTransformation = VisualTransformation.None
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedInputField(
                icon = Icons.Default.AccountCircle,
                placeholder = "Phone",
                value = phone,
                onValueChange = { phone = it },
                visualTransformation = VisualTransformation.None
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedInputField(
                icon = Icons.Default.AccountCircle,
                placeholder = "Bio",
                value = bio,
                onValueChange = { bio = it },
                visualTransformation = VisualTransformation.None
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val user = User(
                        userId = userId,
                        username = username,
                        email = email,
                        avatarUrl = avatarUrl.takeIf { it.isNotBlank() },
                        phone = phone.takeIf { it.isNotBlank() },
                        bio = bio.takeIf { it.isNotBlank() }
                    )
                    updateProfileViewModel.processIntent(
                        UpdateProfileIntent.Update(user)
                    )
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !state.isLoading
            ) {
                Text("Lưu thay đổi", color = Color.White, fontWeight = FontWeight.Bold)
            }
            if (state.error != null) {
                Text(state.error?.localizedMessage ?: "", color = Color.Red)
            }
            if (state.isSuccess) {
                Text("Cập nhật thành công!", color = Color.Green, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Quay lại",
                color = PurpleGrey40,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable { onBack() }
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}
