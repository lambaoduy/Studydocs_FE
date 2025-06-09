package com.example.finalexam.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.viewmodel.AuthViewModel
import com.example.finalexam.ui.theme.Purple40
import com.example.finalexam.ui.theme.creamy

@Composable
fun ForgotPasswordScreen(
    authViewModel: AuthViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by authViewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var sent by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(creamy)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Quên mật khẩu", style = MaterialTheme.typography.h5, color = Purple40)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    authViewModel.processIntent(AuthIntent.ForgotPassword(email))
                    sent = true
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Gửi email đặt lại mật khẩu", color = Color.White)
            }
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }
            if (sent && !state.isLoading && state.error == null) {
                Text("Đã gửi email đặt lại mật khẩu!", color = Color.Green)
            }
            if (state.error?.isNotBlank() == true) {
                Text(state.error ?: "", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onBackClick) {
                Text("Quay lại đăng nhập")
            }
        }
    }
}
