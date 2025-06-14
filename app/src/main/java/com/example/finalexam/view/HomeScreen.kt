package com.example.finalexam.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalexam.ui.theme.FinalExamTheme
import com.example.finalexam.viewmodel.HomeViewModel
import com.example.finalexam.intent.HomeIntent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.Document

@Composable
fun HomeScreen() {
    FinalExamTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            content = { padding ->
                Content(modifier = Modifier.padding(padding))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Studydoc",
                    color = Color(0xFFDDA83F),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Account", modifier = Modifier.size(32.dp))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2B4743),
            titleContentColor = Color(0xFFDDA83F),
            actionIconContentColor = Color(0xFFDDA83F)
        )
    )
}

@Composable
fun BottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("search") },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Find") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("library") },
            icon = { Icon(Icons.AutoMirrored.Filled.LibraryBooks, contentDescription = "Library") },
            label = { Text("Library") }
        )
    }
}

fun onItemSelected(s: String) {}

@Composable
fun Content(modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = viewModel()
    var searchQuery by remember { mutableStateOf("") }
    val id = ""

    LaunchedEffect(Unit) {
        homeViewModel.processIntent(HomeIntent.LoadByUserID(id))
    }

    val uiState by homeViewModel.state.collectAsState()
    val documents = uiState.listDocument

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            label = { Text("Tìm kiếm") },
            leadingIcon = {
                IconButton(onClick = {
                    if (searchQuery.isNotBlank()) {
                        homeViewModel.processIntent(HomeIntent.FindTodo(searchQuery))
                    }
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    // TODO: Hiện Filter
                }) {
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

        ListDocumentView(documents)
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

// Drawer filter bên phải
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RightFilterDrawerScreen(
    isDrawerInitiallyOpen: Boolean = false
) {
    var isDrawerOpen by remember { mutableStateOf(isDrawerInitiallyOpen) }
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Tìm kiếm công việc") },
                    actions = {
                        IconButton(onClick = { isDrawerOpen = true }) {
                            Icon(Icons.Default.FilterList, contentDescription = "Mở bộ lọc")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Tìm kiếm...") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        AnimatedVisibility(
            visible = isDrawerOpen,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it }),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight()
                .width(280.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Bộ lọc", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                FilterItem("Lọc theo ngày") { isDrawerOpen = false }
                FilterItem("Lọc theo trạng thái") { isDrawerOpen = false }
                FilterItem("Đã hoàn thành") { isDrawerOpen = false }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { isDrawerOpen = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Đóng")
                }
            }
        }
    }
}

@Composable
fun FilterItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    )
}
@Preview(showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun HomeScreenPreview() {
    FinalExamTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            content = { padding ->
                ContentPreview(modifier = Modifier.padding(padding))
            }
        )
    }
}
@Composable
fun ContentPreview(modifier: Modifier = Modifier) {
    val sampleDocs = listOf(
        Document("1", "Lập trình Android", "CNTT", "UIT"),
        Document("2", "Trí tuệ nhân tạo", "Khoa học máy tính", "BK"),
        Document("3", "Cơ sở dữ liệu", "Hệ thống thông tin", "HCMUS")
    )

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Tìm kiếm") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = { Icon(Icons.Default.FilterList, contentDescription = "Filter") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ListDocumentView(sampleDocs)
    }
}


//@Preview(showBackground = true, widthDp = 400, heightDp = 700)
//@Composable
//fun RightFilterDrawerScreenPreview() {
//    FinalExamTheme {
//      HomeScreen()
//    }
//}
