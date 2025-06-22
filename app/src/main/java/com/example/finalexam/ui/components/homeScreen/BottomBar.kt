package com.example.finalexam.ui.components.homeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.finalexam.ui.theme.AppColors

@Composable
fun BottomBar(
    onItemSelected: (String) -> Unit // ← callback để chuyển trang
) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = AppColors.TextPrimary) },
            label = { Text("Trang Chủ") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("acount") },
            icon = { Icon(Icons.Default.AccountBox, contentDescription = "Account", tint = AppColors.TextPrimary) },
            label = { Text("Cá nhân") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("library") },
            icon = { Icon(Icons.AutoMirrored.Filled.LibraryBooks, contentDescription = "Library", tint = AppColors.TextPrimary) },
            label = { Text("Thư viện") }
        )
    }
}