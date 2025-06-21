package com.example.finalexam.ui.components.HomeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable
fun BottomBar(
    onItemSelected: (String) -> Unit // ← callback để chuyển trang
) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("acount") },
            icon = { Icon(Icons.Default.AccountBox, contentDescription = "Account") },
            label = { Text("Find") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("library") },
            icon = { Icon(Icons.AutoMirrored.Filled.LibraryBooks, contentDescription = "Library") },
            label = { Text("Library") }
        )
    }
}
