package com.example.finalexam.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.intent.LogoutIntent
import com.example.finalexam.viewmodel.LogoutViewModel

@Composable
fun LogoutScreen(
    logoutViewModel: LogoutViewModel = viewModel(),
    onLogoutSuccess: () -> Unit = {}
) {
    val state by logoutViewModel.state.collectAsState()

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) onLogoutSuccess()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Bạn có chắc chắn muốn đăng xuất?", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { logoutViewModel.processIntent(LogoutIntent.Logout) },
                enabled = !state.isLoading
            ) {
                Text("Đăng xuất")
            }
            if (state.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
            if (state.error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(state.error?.localizedMessage ?: "", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

