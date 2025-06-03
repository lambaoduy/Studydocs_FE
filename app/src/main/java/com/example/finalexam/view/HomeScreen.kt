package com.example.finalexam.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.R
import com.example.finalexam.entity.Document
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.ui.theme.FinalExamTheme
import com.example.finalexam.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    FinalExamTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = {
                BottomBar()
            },
            content = {
                    padding ->  Content( modifier = Modifier.padding(padding))
            }
        )
    }




}
//Ở đây là top bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){

        TopAppBar(
            navigationIcon =  {
                    Icon(Icons.Default.Menu, contentDescription = "Menu",
                        modifier = Modifier.size(70.dp))

                },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {//Row để mọi thứ nằm trên 1 hàng
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
            actions = { Icon(Icons.Default.AccountCircle, contentDescription = "Account",
                modifier = Modifier.size(70.dp))}
            ,
            modifier = Modifier.height(100.dp),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF2B4743),
                titleContentColor = Color(0xFFDDA83F),
                navigationIconContentColor = Color(0xFFDDA83F),
                actionIconContentColor = Color(0xFFDDA83F)
            ),
        )
}
//Ở đây là bottom bar
@Composable
fun BottomBar(){
    NavigationBar {
// Nút Home
        NavigationBarItem(
            selected =  false,
            onClick = { onItemSelected("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
//            Nút search
            selected = false,
//            selected == "search",
            onClick = { onItemSelected("search") },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Find") }
        )
        NavigationBarItem(
//            nút library
            selected = false,
//            selected = selected == "library",
            onClick = { onItemSelected("library") },
            icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Library") },
            label = { Text("Library") }
        )
    }
}

fun onItemSelected(s: String) {

}

//ở đây là content hiển thị nội dung
@Composable
fun Content(modifier: Modifier = Modifier) {
//    tạo viewmodel để xử lý sự kiện cho home
    val homeViewModel: HomeViewModel = viewModel()

    var searchQuery by remember { mutableStateOf("") }// biến này dùng để lưu dữ liệu ng dùng nhập vào thanh search
    val id=""// lấy id user
    homeViewModel.processIntent(HomeIntent.LoadByUserID(id))//load dữ liệu ban đầu theo id user
    val uiState by homeViewModel.state.collectAsState()//lấy state
    val documents = uiState.listDocument//lấy document từ state

    Column(modifier = modifier.padding(16.dp)) {
//        thanh search
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
               searchQuery=it//gán cái gì người dùng nhập vô searchQuery

                if (searchQuery.isNotBlank()) {
//                    dùng home viewmodel để xử lý intent
//                    xử lý như nào thì xem ở lớp viewmodel
                    homeViewModel.processIntent(
                        //đây là homeintent được đóng gói lại từ searchquery. Vì hành động là tìm kiếm nên  dùng lớp Findtodo
//                        xem chi tiết hơn thì xem ở lớp file homintent
                        HomeIntent.FindTodo(searchQuery)
                    )
                }
            },
            label = { Text("Tìm kiếm") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFDDA83F),
                cursorColor = Color(0xFFDDA83F),
                focusedLabelColor = Color(0xFFDDA83F)
            )
        )
// kết thúc thanh search
        Spacer(modifier = Modifier.height(16.dp))
//Hiển thị danh sách documents
        ListDocumentView(documents);
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
//hiển thị 1 document
@Composable
fun DocumentItemView(doc: Document) {
    Text(text = "Title: ${doc.title}")
    Text(text = "Subject: ${doc.subject}")
    Text(text = "University: ${doc.university}")
}

//xem demo
@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    HomeScreen()
}