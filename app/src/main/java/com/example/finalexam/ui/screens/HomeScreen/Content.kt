package com.example.finalexam.ui.screens.HomeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.Document
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.viewmodel.HomeViewModel

@Composable
fun Content(modifier: Modifier = Modifier) {
//    val homeViewModel: HomeViewModel = viewModel()
//    var searchQuery by remember { mutableStateOf("") }
    var isDrawerOpen by remember { mutableStateOf(false) }
    val id = ""

//    LaunchedEffect(Unit) {
//        homeViewModel.processIntent(HomeIntent.LoadByUserID(id))
//    }

//    val uiState by homeViewModel.state.collectAsState()
//    val documents = uiState.listDocument

    Box(modifier = modifier.fillMaxSize()) { // 👈 Wrap lại toàn bộ bằng Box
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            OutlinedTextField(
                value = "",
//                searchQuery,
                onValueChange = {
//                    searchQuery = it
                },
                label = { Text("Tìm kiếm") },
                leadingIcon = {
                    IconButton(onClick ={}
//                    {
//                        if (searchQuery.isNotBlank()) {
//                            homeViewModel.processIntent(HomeIntent.FindTodo(searchQuery))
//                        }
//                    }
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        isDrawerOpen = true
                    },modifier = Modifier
                        .padding(4.dp)) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFDDA83F),
                    cursorColor = Color(0xFFDDA83F),
                    focusedLabelColor = Color(0xFFDDA83F)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

//            ListDocumentView(documents)
        }

        // 👇 Hiển thị drawer filter bên phải
        RightFilterDrawer(
            isVisible = isDrawerOpen,
            onClose = { isDrawerOpen = false }
        )
    }
}

@Composable
fun ListDocumentView(documents: List<Document>) {
    LazyColumn {
        items(documents) { doc ->
            DocumentItemView(doc)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun DocumentItemView(doc: Document) {
    Column {
        Text(text = "Title: ${doc.title}")
        Text(text = "Subject: ${doc.subject}")
        Text(text = "University: ${doc.university}")
    }
}

fun onItemSelected(s: String) {}