package com.example.finalexam.ui.screens.HomeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalexam.ui.theme.FinalExamTheme
import com.example.finalexam.viewmodel.HomeViewModel
import com.example.finalexam.intent.HomeIntent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.R
import com.example.finalexam.entity.Document

@Composable
fun HomeScreen() {
    FinalExamTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            content =
//            {padding ->
//        ContentPreview(modifier = Modifier.padding(padding))}
            { padding ->
                Content(modifier = Modifier.padding(padding))
            }
        )
    }
}

//@Preview(showBackground = true, widthDp = 400, heightDp = 700)
//@Composable
//fun HomeScreenPreview() {
//    FinalExamTheme {
//        Scaffold(
//            topBar = { TopBar() },
//            bottomBar = { BottomBar() },
//            content = { padding ->
//                ContentPreview(modifier = Modifier.padding(padding))
//            }
//        )
//    }
//}
//@Composable
//fun ContentPreview(modifier: Modifier = Modifier) {
//    val sampleDocs = listOf(
//        Document("1", "Lập trình Android", "CNTT", "UIT"),
//        Document("2", "Trí tuệ nhân tạo", "Khoa học máy tính", "BK"),
//        Document("3", "Cơ sở dữ liệu", "Hệ thống thông tin", "HCMUS")
//    )
//
//    Column(modifier = modifier.padding(16.dp)) {
//        OutlinedTextField(
//            value = "",
//            onValueChange = {},
//            label = { Text("Tìm kiếm") },
//            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
//            trailingIcon = { Icon(Icons.Default.FilterList, contentDescription = "Filter") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            shape = RoundedCornerShape(12.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        ListDocumentView(sampleDocs)
//    }
//}

