package com.example.finalexam.view.myLibraryScreen

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.entity.University
import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.ui.theme.AppColors
import com.example.finalexam.viewmodel.UploadDocumentViewModel
import com.example.finalexam.viewmodel.UniversityViewModel
import com.example.finalexam.viewmodel.UploadDocumentViewModelFactory
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import java.io.File
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadDocumentScreen(
    modifier: Modifier = Modifier,
    universityViewModel: UniversityViewModel = viewModel(),
    viewModel: UploadDocumentViewModel = viewModel(
        factory = UploadDocumentViewModelFactory(universityViewModel)
    ),
    onBackClick: () -> Unit = {},
    onUploadClick: (UploadDocument?) -> Unit = {},
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val document = UploadDocument(
                id = UUID.randomUUID().toString(),
                name = getFileName(context, uri),
                fileUrl = uri.toString()
            )
            viewModel.processIntent(UploadDocumentIntent.SetSelectedDocument(document))
        }
    }

    val state by viewModel.state.collectAsState()
    val universityState by universityViewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        universityViewModel.loadUniversities()
    }

    LaunchedEffect(universityState.error) {
        universityState.error?.let { err ->
            if (err.contains("tồn tại")) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(err)
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // ===== AppBar =====
        TopAppBar(
            title = { Text("Studocu") },
            navigationIcon = {
                IconButton(onClick = {
                    viewModel.processIntent(UploadDocumentIntent.Back)
                    onBackClick()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // ===== Nhập tiêu đề =====
            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.processIntent(UploadDocumentIntent.SetTitle(it)) },
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // ===== Nhập mô tả =====
            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.processIntent(UploadDocumentIntent.SetDescription(it)) },
                label = { Text("Mô tả") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(Modifier.height(16.dp))

            // ===== Nút chọn tài liệu =====
            Button(
                onClick = { launcher.launch("*/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.selectedDocument == null) "Chọn tài liệu từ máy" else "Chọn tài liệu khác")
            }

            Spacer(Modifier.height(16.dp))

            // ===== Hiển thị file đã chọn =====
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
                        Text(doc.name, modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            viewModel.processIntent(UploadDocumentIntent.RemoveSelectedDocument)
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Bỏ tài liệu")
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // ===== Hảo làm phần này: Chọn trường và môn học =====
            if (state.universityList.isNotEmpty()) {
                UniversitySelectionSection(
                    university = state.university ?: state.universityList.first(),
                    universityList = state.universityList,
                    onUniversitySelected = { id ->
                        viewModel.processIntent(UploadDocumentIntent.SelectUniversity(id))
                    },
                    onSubjectSelected = { index ->
                        viewModel.processIntent(UploadDocumentIntent.SelectSubjectIndex(index))
                    },
                    onAddSubject = { name ->
                        viewModel.processIntent(UploadDocumentIntent.AddSubject(name))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(24.dp))

            // ===== Nút Upload =====
            Button(
                onClick = {
                    viewModel.processIntent(UploadDocumentIntent.Upload)
                    onUploadClick(state.selectedDocument)
                },
                enabled = state.selectedDocument != null
                        && state.title.isNotBlank()
                        && state.description.isNotBlank()
                        && state.university?.selectedSubject?.isNotBlank() == true,
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

        SnackbarHost(hostState = snackbarHostState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversitySelectionSection(
    modifier: Modifier = Modifier,
    university: University,
    universityList: List<University>,
    onUniversitySelected: (String) -> Unit,
    onSubjectSelected: (Int) -> Unit,
    onAddSubject: (String) -> Unit
) {
    var universityExpanded by remember { mutableStateOf(false) }
    var subjectExpanded by remember { mutableStateOf(false) }
    var showCreateSubjectDialog by remember { mutableStateOf(false) }
    var newSubjectName by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        // Dropdown chọn trường
        Text(
            text = "Trường đại học",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = universityExpanded,
            onExpandedChange = { universityExpanded = !universityExpanded }
        ) {
            TextField(
                value = university.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = universityExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = universityExpanded,
                onDismissRequest = { universityExpanded = false }
            ) {
                universityList.forEach { uni ->
                    DropdownMenuItem(
                        text = { Text(uni.name) },
                        onClick = {
                            onUniversitySelected(uni.id)
                            universityExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Dropdown chọn môn học
        Text(
            text = "Môn học",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = subjectExpanded,
            onExpandedChange = { subjectExpanded = !subjectExpanded }
        ) {
            TextField(
                value = university.selectedSubject,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = subjectExpanded,
                onDismissRequest = { subjectExpanded = false }
            ) {
                university.subjects.forEachIndexed { index, subject ->
                    DropdownMenuItem(
                        text = { Text(subject) },
                        onClick = {
                            onSubjectSelected(index)
                            subjectExpanded = false
                        }
                    )
                }
                DropdownMenuItem(
                    text = { Text("+ Thêm môn học mới") },
                    onClick = {
                        showCreateSubjectDialog = true
                        subjectExpanded = false
                    }
                )
            }
        }
    }

    // Dialog thêm môn học mới
    if (showCreateSubjectDialog) {
        AlertDialog(
            onDismissRequest = { showCreateSubjectDialog = false },
            title = { Text("Thêm môn học mới") },
            text = {
                TextField(
                    value = newSubjectName,
                    onValueChange = { newSubjectName = it },
                    label = { Text("Tên môn học") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newSubjectName.isNotBlank()) {
                            onAddSubject(newSubjectName)
                            newSubjectName = ""
                            showCreateSubjectDialog = false
                        }
                    }
                ) {
                    Text("Thêm")
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

