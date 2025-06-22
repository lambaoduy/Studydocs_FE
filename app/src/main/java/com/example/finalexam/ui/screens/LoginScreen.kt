package com.example.finalexam.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.R
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.ui.theme.AppColors
import com.example.finalexam.viewmodel.AuthViewModel

// thiện làm: LoginScreen theo MVI
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val state by authViewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "Đăng nhập",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.BlueMid
            )
            Text(
                text = "Đăng nhập để tiếp tục",
                fontSize = 14.sp,
                color = AppColors.TextSecondary,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            RoundedInputField(
                icon = Icons.Default.Email,
                iconTint = AppColors.BlueMid,
                placeholder = "Email",
                value = email,
                onValueChange = { email = it },
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedInputField(
                icon = Icons.Default.Lock,
                iconTint = AppColors.BlueMid,
                placeholder = "Mật khẩu",
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    authViewModel.processIntent(AuthIntent.Login(email, password))
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.BlueMid),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(6.dp, RoundedCornerShape(50))
            ) {
                Text("Đăng nhập", color = AppColors.Surface, fontWeight = FontWeight.Bold)
            }
            if (state.error?.isNotBlank() == true) {
                Text(state.error ?: "", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Quên mật khẩu?",
                    color = AppColors.TextSecondary,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable { onForgotPasswordClick() }
                )

                Text(
                    text = "Tạo tài khoản mới",
                    color = AppColors.TextSecondary,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}