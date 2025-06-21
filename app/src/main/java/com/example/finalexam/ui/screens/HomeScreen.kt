package com.example.finalexam.ui.screens

import TopBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalexam.entity.Document
import com.example.finalexam.ui.components.HomeScreen.BottomBar
import com.example.finalexam.ui.components.HomeScreen.Content
import com.example.finalexam.ui.components.HomeScreen.ListDocumentView
import com.example.finalexam.ui.theme.FinalExamTheme

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

