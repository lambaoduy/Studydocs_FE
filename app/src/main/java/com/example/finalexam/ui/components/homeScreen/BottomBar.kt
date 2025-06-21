package com.example.finalexam.ui.components.homeScreen

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
    currentRoute: String, // ← thêm route hiện tại
    onItemSelected: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { onItemSelected("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute == "account",
            onClick = { onItemSelected("account") },
            icon = { Icon(Icons.Default.AccountBox, contentDescription = "Account") },
            label = { Text("Account") }
        )
        NavigationBarItem(
            selected = currentRoute == "library",
            onClick = { onItemSelected("library") },
            icon = { Icon(Icons.AutoMirrored.Filled.LibraryBooks, contentDescription = "Library") },
            label = { Text("Library") }
        )
    }
}

