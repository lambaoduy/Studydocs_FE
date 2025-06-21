//package com.example.finalexam.ui.components.notification
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.ErrorOutline
//import androidx.compose.material.icons.outlined.Refresh
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import com.example.finalexam.ui.screens.NotificationColors
//
//@Composable
//fun ErrorState(
//    message: String,
//    onRetry: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(32.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Icon(
//            imageVector = Icons.Outlined.ErrorOutline,
//            contentDescription = null,
//            modifier = Modifier.size(80.dp),
//            tint = NotificationColors.Error
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Có lỗi xảy ra",
//            style = MaterialTheme.typography.titleMedium,
//            color = NotificationColors.OnSurface,
//            fontWeight = FontWeight.Medium
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = message,
//            style = MaterialTheme.typography.bodyMedium,
//            color = NotificationColors.OnSurfaceVariant
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            onClick = onRetry,
//            colors = ButtonDefaults.buttonColors(
//                containerColor = NotificationColors.Primary
//            )
//        ) {
//            Icon(
//                Icons.Outlined.Refresh,
//                contentDescription = null,
//                modifier = Modifier.size(18.dp)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text("Thử lại")
//        }
//    }
//}