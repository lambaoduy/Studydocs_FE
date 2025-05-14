package com.example.finalexam.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import com.example.finalexam.ui.theme.*

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit = {}
) {
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
            Spacer(modifier = Modifier.height(40.dp))

            // Image placeholder
//            Image(
//                painter = painterResource(id = R.mipmap.register_illustration),
//                contentDescription = "Register Illustration",
//                modifier = Modifier
//                    .height(200.dp)
//                    .fillMaxWidth()
//            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Create New Account",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Purple40
            )

            Spacer(modifier = Modifier.height(24.dp))

            RoundedInputField(
                icon = Icons.Default.Person,
                placeholder = "Subeo"
            )

            Spacer(modifier = Modifier.height(12.dp))

            RoundedInputField(
                icon = Icons.Default.Email,
                placeholder = "subeo13@gmail.com"
            )

            Spacer(modifier = Modifier.height(12.dp))

            RoundedInputField(
                icon = Icons.Default.Lock,
                placeholder = "*****",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: Handle registration */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple40),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("SIGN UP", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onLoginClick() }
            ) {
                Text(
                    text = "Already registered? ",
                    color = PurpleGrey40
                )
                Text(
                    text = "Login",
                    color = Purple40,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegisterScreenPreview() {
    FinalExamTheme {
        RegisterScreen()
    }
}