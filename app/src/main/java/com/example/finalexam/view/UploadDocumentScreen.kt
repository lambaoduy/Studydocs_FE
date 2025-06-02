package com.example.finalexam.ui.myLibraryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalexam.ui.theme.AppColors
import com.example.finalexam.ui.theme.FinalExamTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

data class UploadDocument(
    val id: String,
    val name: String,
    var isSelected: Boolean = false
)

data class University(
    val id: String,
    val name: String,
    val courses: List<String>,
    var selectedCourseIndex: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadDocumentScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadDocumentViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onUploadClick: (List<UploadDocument>) -> Unit = {},
    onCourseSelected: (Int) -> Unit = {},
) {
    // Lấy state từ ViewModel
    val state by viewModel.state.collectAsState()

    // Khi vào màn hình, tự động load dữ liệu mẫu
    LaunchedEffect(Unit) {
        viewModel.loadMockData()
    }

    Column(modifier = modifier.fillMaxSize().background(AppColors.Background)) {
        TopAppBar(
            title = { Text("Studocu") },
            navigationIcon = {
                IconButton(onClick = {
                    // Gửi Intent quay lại lên ViewModel
                    viewModel.processIntent(UploadDocumentIntent.Back)
                    onBackClick()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .weight(1f)
        ) {
            // Documents Section
            Text(
                text = "Documents",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            DocumentSelectionList(
                documents = state.documents,
                onItemSelected = { docId ->
                    // Gửi Intent chọn tài liệu lên ViewModel
                    viewModel.processIntent(UploadDocumentIntent.SelectDocument(docId))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // University Section
            state.university?.let { uni ->
                UniversitySelectionSection(
                    university = uni,
                    onCourseSelected = { index ->
                        // Gửi Intent chọn khóa học lên ViewModel
                        viewModel.processIntent(UploadDocumentIntent.SelectCourse(index))
                        onCourseSelected(index)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(24.dp))

            // Upload Button
            Button(
                onClick = {
                    // Gửi Intent upload lên ViewModel
                    viewModel.processIntent(UploadDocumentIntent.Upload)
                    onUploadClick(state.documents.filter { it.isSelected })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = Color.White
                )
            ) {
                if (state.isUploading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Upload Documents")
                }
            }
        }
    }
}

@Composable
private fun DocumentSelectionList(
    modifier: Modifier = Modifier,  // Modifier first
    documents: List<UploadDocument>,
    onItemSelected: (String) -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 2.dp
    ) {
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(documents) { doc ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onItemSelected(doc.id) }
                ) {
                    Checkbox(
                        checked = doc.isSelected,
                        onCheckedChange = { onItemSelected(doc.id) }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = doc.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UniversitySelectionSection(
    modifier: Modifier = Modifier,  // Modifier first
    university: University,
    onCourseSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = "University",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = university.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(16.dp))

                // Course Dropdown
                Text(
                    text = "Course",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                ExposedDropdownMenuBox(
                    modifier = Modifier.padding(top = 8.dp),
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        value = university.courses[university.selectedCourseIndex],
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        university.courses.forEachIndexed { index, course ->
                            DropdownMenuItem(
                                text = { Text(course) },
                                onClick = {
                                    onCourseSelected(index)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun UploadDocumentScreenPreview() {
    val mockDocuments = listOf(
        UploadDocument("1", "Chao_cau.docx", true),
        UploadDocument("2", "Bai_tap.pdf"),
        UploadDocument("3", "Assignment.docx")
    )

    val mockUniversity = University(
        id = "uni1",
        name = "Đại học Nông Lâm TP.HCM",  // Fixed Vietnamese typo
        courses = listOf("Công nghệ thông tin", "Khoa học dữ liệu", "Kỹ thuật phần mềm"),
        selectedCourseIndex = 0
    )

    FinalExamTheme {
        UploadDocumentScreen(
            documents = mockDocuments,
            university = mockUniversity,
            onBackClick = { println("Back clicked") },
            onUploadClick = { selected ->
                println("Uploading: ${selected.joinToString { it.name }}")
            },
            onCourseSelected = { index ->
                println("Selected course index: $index")
            }
        )
    }
}
