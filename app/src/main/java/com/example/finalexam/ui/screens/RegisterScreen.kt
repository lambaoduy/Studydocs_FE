package com.example.finalexam.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.R
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.ui.theme.AppColors
import com.example.finalexam.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginClick: () -> Unit = {},
    onRegisterSuccess: () -> Unit
) {
    val state by authViewModel.state.collectAsState()
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onRegisterSuccess()
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
                text = "Đăng ký tài khoản",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.BlueMid
            )
            Spacer(modifier = Modifier.height(24.dp))
            RoundedInputField(
                icon = Icons.Default.Person,
                placeholder = "Họ tên",
                value = fullName,
                onValueChange = { fullName = it },
                iconTint = AppColors.BlueMid
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedInputField(
                icon = Icons.Default.Email,
                placeholder = "Email",
                value = email,
                onValueChange = { email = it },
                iconTint = AppColors.BlueMid
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedInputField(
                icon = Icons.Default.Lock,
                placeholder = "Mật khẩu",
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                iconTint = AppColors.BlueMid
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    authViewModel.processIntent(
                        AuthIntent.Register(fullName, email, password)
                    )
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.BlueMid),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Đăng ký", color = Color.White, fontWeight = FontWeight.Bold)
            }
            if (state.error?.isNotBlank() == true) {
                Text(state.error ?: "", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onLoginClick() }
            ) {
                Text(
                    text = "Bạn đã có tài khoản?",
                    color = AppColors.TextSecondary
                )
                Text(
                    text = "Đăng nhập",
                    color = AppColors.BlueMid,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

// Định nghĩa RoundedInputField duy nhất ở đây
@Composable
fun RoundedInputField(
    icon: ImageVector,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    iconTint: Color
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.BlueMid
            )
        },
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    )
}
