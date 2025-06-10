package com.example.finalexam.view.myLibraryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.finalexam.ui.theme.FinalExamTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.University
import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.ui.theme.AppColors
import com.example.finalexam.viewmodel.UploadDocumentViewModel
import kotlin.collections.filter
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadDocumentScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadDocumentViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onUploadClick: (UploadDocument?) -> Unit = {},
    onCourseSelected: (Int) -> Unit = {},
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            // Tạo UploadDocument từ URI đã chọn
            val document = UploadDocument(
                id = UUID.randomUUID().toString(),
                name = getFileName(context, uri),
                fileUrl = uri.toString()
            )
            viewModel.processIntent(UploadDocumentIntent.SetSelectedDocument(document))
        }
    }

    val state by viewModel.state.collectAsState()
    var showCreateSubjectDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadMockData()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        TopAppBar(
            title = { Text("Studocu") },
            navigationIcon = {
                IconButton(onClick = {
                    viewModel.processIntent(UploadDocumentIntent.Back)
                    onBackClick()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
            // Title Input
            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.processIntent(UploadDocumentIntent.SetTitle(it)) },
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Description Input
            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.processIntent(UploadDocumentIntent.SetDescription(it)) },
                label = { Text("Mô tả") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(Modifier.height(16.dp))

            // Nút chọn tài liệu từ máy
            Button(
                onClick = {
                    launcher.launch("*/*") // Cho phép chọn tất cả các loại file
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.selectedDocument == null) "Chọn tài liệu từ máy" else "Chọn tài liệu khác")
            }

            Spacer(Modifier.height(16.dp))

            // Hiển thị file đã chọn (nếu có)
            state.selectedDocument?.let { doc ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    tonalElevation = 2.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = doc.name,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            // Bỏ file đã chọn
                            viewModel.processIntent(UploadDocumentIntent.RemoveSelectedDocument)
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Bỏ tài liệu")
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // University Section
            state.university?.let { uni ->
                UniversitySelectionSection(
                    university = uni,
                    onCourseSelected = { index ->
                        viewModel.processIntent(UploadDocumentIntent.SelectCourse(index))
                        onCourseSelected(index)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(16.dp))

            // Subject Section
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Môn học",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Dropdown chọn môn học
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        TextField(
                            value = state.subject,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Chọn môn học") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = false,
                            onDismissRequest = { }
                        ) {
                            state.subjectList.forEach { subject ->
                                DropdownMenuItem(
                                    text = { Text(subject) },
                                    onClick = {
                                        viewModel.processIntent(
                                            UploadDocumentIntent.SelectSubject(
                                                subject
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                    // Nút tạo môn học mới
                    Button(
                        onClick = { showCreateSubjectDialog = true },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Tạo môn mới")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Upload Button
            Button(
                onClick = {
                    viewModel.processIntent(UploadDocumentIntent.Upload)
                    onUploadClick(state.selectedDocument)
                },
                enabled = state.selectedDocument != null && state.title.isNotBlank() && state.description.isNotBlank() && state.subject.isNotBlank(),
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
                    Text("Upload Document")
                }
            }
        }
    }

    // Dialog tạo môn học mới
    if (showCreateSubjectDialog) {
        var newSubject by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showCreateSubjectDialog = false },
            title = { Text("Tạo môn học mới") },
            text = {

                OutlinedTextField(
                    value = newSubject,
                    onValueChange = { newSubject = it },
                    label = { Text("Tên môn học") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Gửi intent tạo môn học mới
                        viewModel.processIntent(UploadDocumentIntent.CreateSubject(newSubject))
                        showCreateSubjectDialog = false
                    }
                ) {
                    Text("Tạo")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateSubjectDialog = false }) {
                    Text("Hủy")
                }
            }
        )
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

// Hàm helper để lấy tên file từ URI
private fun getFileName(context: Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    result = cursor.getString(nameIndex)
                }
            }
        } finally {
            cursor?.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf(File.separator)
        if (cut != -1) {
            result = result?.substring(cut!! + 1)
        }
    }
    return result ?: "Unknown file"
}

