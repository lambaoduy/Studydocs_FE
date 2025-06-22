@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.finalexam.ui.components.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.finalexam.R
import com.example.finalexam.ui.theme.AppColors

@Composable
fun TopBar(
    navigateToProfile: () -> Unit,
    navigateToNotification: () -> Unit,
    isLoggedIn: Boolean,
    avatarUrl: String?,
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(70.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navigateToNotification()
            }) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Bell",
                    modifier = Modifier.size(32.dp)
                )
            }
            if (!avatarUrl.isNullOrBlank()) {
                // Hiển thị avatar từ URL
                coil.compose.AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { expanded = true },
                )
            } else {
                // Hiển thị icon mặc định
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { expanded = true }
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                if (isLoggedIn) {
                    DropdownMenuItem(
                        text = { Text("Trang cá nhân") },
                        onClick = {
                            expanded = false
                            navigateToProfile()
                        }
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text("Đăng nhập") },
                        onClick = {
                            expanded = false
                            navigateToLogin()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Đăng ký") },
                        onClick = {
                            expanded = false
                            navigateToRegister()
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppColors.Background,
            actionIconContentColor = AppColors.BlueMid
        )
    )
}