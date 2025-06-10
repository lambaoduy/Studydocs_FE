package com.example.finalexam.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.viewmodel.AuthViewModel
import com.example.finalexam.ui.theme.*

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    val state by authViewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
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
                imageVector = Icons.Default.Lock,
                contentDescription = "Login Icon",
                tint = Purple40,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Purple40
            )
            Text(
                text = "Sign in to continue",
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
            Spacer(modifier = Modifier.height(12.dp))
            RoundedInputField(
                icon = Icons.Default.Lock,
                placeholder = "Password",
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Quên mật khẩu?",
                    color = Purple40,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { onForgotPasswordClick() }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    authViewModel.processIntent(AuthIntent.Login(email, password))
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple40),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("LOGIN", color = Color.White, fontWeight = FontWeight.Bold)
            }
            if (state.error?.isNotBlank() == true) {
                Text(state.error ?: "", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Create New Account",
                color = PurpleGrey40,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    FinalExamTheme {
        LoginScreen()
    }
} 