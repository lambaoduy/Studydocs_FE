@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.finalexam.ui.screens.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.finalexam.R
import com.example.finalexam.network.AuthFilter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

@Composable
fun TopBar(
    avatarUrl: String? = null, // truyền url ảnh đại diện nếu có
    onLogin: () -> Unit = {},
    onRegister: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val isLoggedIn = AuthFilter.isLoggedIn(context)
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Hiển thị avatar nếu đã đăng nhập và có avatarUrl, nếu không thì icon mặc định
                if (isLoggedIn && !avatarUrl.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(avatarUrl),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
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
                // DropdownMenu khi bấm vào avatar/icon user
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
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(70.dp)
                )
                Text(
                    text = "Studydoc",
                    color = Color(0xFFDDA83F),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            IconButton(onClick = {
                 //action thông báo
                }) {
                Icon(Icons.Filled.Notifications, contentDescription = "Bell", modifier = Modifier.size(32.dp))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2B4743),
            titleContentColor = Color(0xFFDDA83F),
            actionIconContentColor = Color(0xFFDDA83F)
        )
    )
}