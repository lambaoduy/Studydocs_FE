package com.example.finalexam.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.finalexam.R

@Composable
fun UserProfileOverviewScreen(
    userId: String,
    avatarUrl: String? = null,
    followerCount: Int = 0,
    followingCount: Int = 0,
    uploadCount: Int = 0,
    downloadCount: Int = 0,
    onShowProfile: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ảnh nền user
        if (avatarUrl != null && avatarUrl.isNotBlank()) {
            // TODO: Load ảnh từ url bằng Coil hoặc Glide
        } else {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Số follow và follower
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$followingCount", style = MaterialTheme.typography.titleMedium)
                Text(text = "Đang theo dõi", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$followerCount", style = MaterialTheme.typography.titleMedium)
                Text(text = "Người theo dõi", style = MaterialTheme.typography.bodySmall)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        // Nút xem thông tin cá nhân
        Button(onClick = onShowProfile) {
            Text("Xem thông tin cá nhân")
        }
        Spacer(modifier = Modifier.height(24.dp))
        // Báo cáo số lượng upload/download
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$uploadCount", style = MaterialTheme.typography.titleMedium)
                Text(text = "Tài liệu đã upload", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$downloadCount", style = MaterialTheme.typography.titleMedium)
                Text(text = "Tài liệu đã download", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

