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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.finalexam.R
import com.example.finalexam.ui.theme.AppColors

@Composable
fun TopBar(
    navigateToNotification: () -> Unit,
    isLoggedIn: Boolean = false,
    avatarUrl: String? = null,
    onLogin: () -> Unit = {},
    onRegister: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(70.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { navigateToNotification() }) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Bell",
                    modifier = Modifier.size(32.dp)
                )
            }
            if (isLoggedIn && !avatarUrl.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(avatarUrl),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { expanded = true }
                )
            } else {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Account",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { expanded = true }
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                if (isLoggedIn) {
                    DropdownMenuItem(
                        text = { Text("Đăng xuất") },
                        onClick = {
                            expanded = false
                            onLogout()
                        }
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text("Đăng nhập") },
                        onClick = {
                            expanded = false
                            onLogin()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Đăng ký") },
                        onClick = {
                            expanded = false
                            onRegister()
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

@Preview
@Composable
fun TopBarPreview() {
    TopBar(navigateToNotification = {})
}