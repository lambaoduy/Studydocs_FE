package com.example.finalexam.ui.components.homeScreen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.Document
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.ui.theme.AppColors
import com.example.finalexam.viewmodel.HomeViewModel
import com.example.finalexam.viewmodel.HomeViewModelFactory

@Composable
fun Content(modifier: Modifier = Modifier,
            onNavigateToDocumentDetail: (String) -> Unit) {
    val app = LocalContext.current.applicationContext as Application
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(app)
    )
    var searchQuery by remember { mutableStateOf("") }
    var isDrawerOpen by remember { mutableStateOf(false) }
    val id = ""
    var school by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        homeViewModel.processIntent(HomeIntent.GetAllTodo)
    }

    val uiState by homeViewModel.state.collectAsState()
    val documents = uiState.listDocument


    Box(modifier = modifier.fillMaxSize()) { // 👈 Wrap lại toàn bộ bằng Box
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            OutlinedTextField(
                value =
                    searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                label = { Text("Tìm kiếm") },
                leadingIcon = {
                    IconButton(onClick = {
                        homeViewModel.processIntent(
                            HomeIntent.FindWithFilters(
                                keyword = searchQuery.takeIf { it.isNotBlank() },
                                school = school.takeIf { it.isNotBlank() },
                                subject = subject.takeIf { it.isNotBlank() }
                            )
                        )
                    })
                    {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                trailingIcon = {
                    Box(modifier = Modifier.padding(4.dp)) {
                        IconButton(
                            onClick = { isDrawerOpen = true }
                        ) {
                            Icon(Icons.Default.FilterList, contentDescription = "Filter")
                        }

                        // Tính số lượng bộ lọc đang được bật
                        val count = listOf(school, subject).count { it.isNotBlank() }

                        // Chỉ hiển thị nếu có ít nhất 1 filter
                        if (count > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 2.dp, end = 2.dp)
                                    .height(16.dp)
                                    .width(16.dp)
                                    .background(Color.Black, shape = RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = count.toString(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                ,
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

            ListDocumentView(documents, onNavigateToDetail = onNavigateToDocumentDetail)
        }

        // 👇 Hiển thị drawer filter bên phải
        RightFilterDrawer(
            isVisible = isDrawerOpen,
            school = school,
            onSchoolChange = { school = it },
            subject = subject,
            onSubjectChange = { subject = it },
            onClose = { isDrawerOpen = false }
        )
    }
}


@Composable
fun ListDocumentView(documents: List<Document>,
                     onNavigateToDetail: (String) -> Unit) {
    LazyColumn {
        items(documents) { doc ->
            DocumentItemView(doc, onClick = { onNavigateToDetail(doc.id) })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



@Composable
fun DocumentItemView(
    document: Document,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = document.title,
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Trường: ${document.university ?: "Không rõ"}",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.TextSecondary
            )
            Text(
                text = "Môn học: ${document.subject ?: "Không rõ"}",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.TextSecondary
            )
        }
    }
}


fun onItemSelected(s: String) {}
@Preview(showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun ContentPreview() {
//    Content()
}