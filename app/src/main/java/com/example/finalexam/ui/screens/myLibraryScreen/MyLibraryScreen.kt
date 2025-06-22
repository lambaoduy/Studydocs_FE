package com.example.finalexam.ui.screens.myLibraryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.Document
import com.example.finalexam.intent.MyLibraryIntent
import com.example.finalexam.navigation.NavigationEvent
import com.example.finalexam.ui.components.homeScreen.RightFilterDrawer
import com.example.finalexam.ui.theme.AppColors
import com.example.finalexam.viewmodel.MyLibraryViewModel


@Composable
fun MyLibraryScreen(
    viewModel: MyLibraryViewModel = viewModel(),
    onNavigateToUpload: () -> Unit,
    onNavigateToDocumentDetail: (String) -> Unit, // Đây là hàm nhận documentId
    onNavigateToHome: () -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val state by viewModel.state.collectAsState()
    var isDrawerOpen by remember { mutableStateOf(false) }
    var universityFilter by remember { mutableStateOf("") }
    var subjectFilter by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToUpload -> onNavigateToUpload()
                is NavigationEvent.NavigateToDocumentDetail -> onNavigateToDocumentDetail(event.documentId)
                is NavigationEvent.NavigateToHome -> onNavigateToHome()
                else -> {}
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.processIntent(MyLibraryIntent.Refresh)
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background)
                .padding(16.dp)
        ) {
            // Panel 1: Top Bar với nút quay về trang chủ và tiêu đề
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onNavigateToHome()
//                        viewModel.processIntent(MyLibraryIntent.NavigateToHome)
                    }, modifier = Modifier
                        .padding(8.dp)
                        .size(48.dp)

                ) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Quay về trang chủ",
                        tint = AppColors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Thư viện của tôi",
                    style = MaterialTheme.typography.titleLarge,
                    color = AppColors.Primary
                )

                // Placeholder để cân bằng layout
                Spacer(modifier = Modifier.size(48.dp))
            }

            // Panel 2: Nút thêm tài liệu với viền nét đứt
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        onNavigateToUpload()
                    }
                    .drawBehind {
                        drawRoundRect(
                            color = AppColors.Primary,
                            style = Stroke(
                                width = 2f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            ),
                            cornerRadius = CornerRadius(16.dp.toPx())
                        )
                    },
                shape = RoundedCornerShape(16.dp),
                color = AppColors.Surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Tải tài liệu lên",
                        tint = AppColors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                    Text(
                        text = "Thêm tài liệu mới",
                        style = MaterialTheme.typography.titleMedium,
                        color = AppColors.Primary
                    )
                }
            }

            // --- TabRow: Đã đăng & Đã lưu ---
            val tabs = listOf("Tài liệu đã đăng", "Tài liệu đã lưu")

            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Nội dung mỗi tab ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.Surface, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                // Thanh tìm kiếm
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    label = { Text("Tìm kiếm") },
                    leadingIcon = {
                        IconButton(onClick = {
                            viewModel.processIntent(
                                MyLibraryIntent.FindWithFilters(
                                    keyword = searchQuery.takeIf { it.isNotBlank() },
                                    university = universityFilter.takeIf { it.isNotBlank() },
                                    subject = subjectFilter.takeIf { it.isNotBlank() }
                                ))
                        }) {
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

                            val count =
                                listOf(universityFilter, subjectFilter).count { it.isNotBlank() }
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

                // Danh sách tài liệu
                val documentsToShow = if (selectedTabIndex == 0) {
                    state.documents
                } else {
                    state.documentsSave
                }
                println("MyLibraryScreen - xem duoc: " + documentsToShow.size)
                println("MyLibraryScreen - trong state: " + state.documentsSave.size)

                DocumentList(
                    documents = documentsToShow,
                    onDocumentClick = { document -> // THAY ĐỔI Ở ĐÂY: nhận document
                        onNavigateToDocumentDetail(document.id) // Gọi với document.id
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        // Hiển thị drawer filter bên phải
        RightFilterDrawer(
            isVisible = isDrawerOpen,
            school = universityFilter,
            onSchoolChange = { universityFilter = it },
            subject = subjectFilter,
            onSubjectChange = { subjectFilter = it },
            onClose = { isDrawerOpen = false }
        )
    }
}


@Composable
fun DocumentList(
    documents: List<Document>,
    onDocumentClick: (Document) -> Unit, // Callback nhận Document
    modifier: Modifier = Modifier
) {
    println("📋 Danh sách render: ${documents.size} item")
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(documents) { document ->
            DocumentItem(
                document = document,
                onClick = { onDocumentClick(document) } // Truyền document vào callback
            )
        }
    }
}


@Composable
fun DocumentItem(
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