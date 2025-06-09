package com.example.finalexam.ui.myLibraryScreen

import MyLibraryViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.Document
import com.example.finalexam.intent.MyLibraryIntent

import com.example.finalexam.ui.theme.*

@Composable
fun MyLibraryScreen(
    viewModel:  MyLibraryViewModel = viewModel()
) {
    // Lấy state từ ViewModel
    val state by viewModel.state.collectAsState()

    // Khi vào màn hình, tự động load dữ liệu
    LaunchedEffect(Unit) {
        viewModel.loadDocuments()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Panel tìm kiếm
            SearchPanel(
                query = state.searchQuery,
                onQueryChange = { query ->
                    // Gửi Intent tìm kiếm lên ViewModel
                    viewModel.processIntent(MyLibraryIntent.Search(query))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nút upload tài liệu
            UploadDocumentButton(
                onClick = {
                    // Gửi Intent upload lên ViewModel
                    viewModel.processIntent(MyLibraryIntent.UploadDocument)
                },
                onAddClick = { /* chưa dùng */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = AppColors.Surface,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tài liệu đã đăng tải",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Hiển thị danh sách tài liệu từ State
                    DocumentList(
                        documents = state.documents,
                        onDocumentClick = { doc ->
                            // Gửi Intent chọn tài liệu lên ViewModel
                            viewModel.processIntent(MyLibraryIntent.SelectDocument(doc))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchPanel(
    query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = AppColors.SearchTextColor,
            unfocusedTextColor = AppColors.SearchTextColor,
            focusedContainerColor = AppColors.SearchBackground,
            unfocusedContainerColor = AppColors.SearchBackground,
            focusedIndicatorColor = AppColors.SearchFocusedBorderColor,
            unfocusedIndicatorColor = AppColors.SearchBorderColor,
            cursorColor = AppColors.Primary
        ),
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Tìm kiếm tài liệu ...") },

        )
}

@Composable
fun UploadDocumentButton(
    modifier: Modifier = Modifier, onClick: () -> Unit, onAddClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.UploadBoxBackground)
            .drawBehind {
                val strokeWidth = 4f
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                val cornerRadiusPx = 16.dp.toPx()
                drawRoundRect(
                    color = AppColors.BorderColor,
                    style = Stroke(width = strokeWidth, pathEffect = pathEffect),
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            }
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tải tài liệu lên",
            color = AppColors.TextPrimary,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun DocumentList(
    documents: List<Document>, onDocumentClick: (Document) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(documents) { doc ->
            DocumentItem(
                document = doc, onClick = { onDocumentClick(doc) })
        }
    }
}

@Composable
fun DocumentItem(
    document: Document, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBackground),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(AppColors.CardBackground)
        ) {
            Text(text = document.title, style = MaterialTheme.typography.titleMedium)
            Text(text = document.author, style = MaterialTheme.typography.bodySmall)
            Text(
                text = "Ngày tạo: ${document.createdDate}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun UploadedDocumentsPanel(
    documents: List<Document>, onDocumentClick: (Document) -> Unit, modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = AppColors.Surface,
        tonalElevation = 2.dp,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tài liệu đã đăng tải",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.TextPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            DocumentList(
                documents = documents, onDocumentClick = onDocumentClick
            )
        }
    }
}

