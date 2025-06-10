package com.example.finalexam.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.viewmodel.AuthViewModel
import com.example.finalexam.ui.theme.*

@Composable
fun ForgotPasswordScreen(
    authViewModel: AuthViewModel = viewModel(),
    onBackToLogin: () -> Unit = {}
) {
    val state by authViewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var sent by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            sent = true
            // Chờ 1.5s rồi chuyển về login
            kotlinx.coroutines.delay(1500)
            onBackToLogin()
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
            Spacer(modifier = Modifier.height(40.dp))
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Forgot Password Icon",
                tint = Purple40,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "Quên mật khẩu",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Purple40
            )
            Text(
                text = "Nhập email để lấy lại mật khẩu",
                fontSize = 14.sp,
                color = PurpleGrey40,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            RoundedInputField(
                icon = Icons.Default.Email,
                placeholder = "Email",
                value = email,
                onValueChange = { email = it }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    authViewModel.processIntent(AuthIntent.ForgotPassword(email))
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple40),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !state.isLoading && !sent
            ) {
                Text("Gửi email xác nhận", color = Color.White, fontWeight = FontWeight.Bold)
            }
            if (state.error?.isNotBlank() == true) {
                Text(state.error ?: "", color = Color.Red)
            }
            if (sent) {
                Text("Đã gửi email xác nhận!", color = Color.Green, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Quay lại đăng nhập",
                color = PurpleGrey40,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable { onBackToLogin() }
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    FinalExamTheme {
        ForgotPasswordScreen()
    }
}
