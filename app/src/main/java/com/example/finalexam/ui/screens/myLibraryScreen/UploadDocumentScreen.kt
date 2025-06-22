package com.example.finalexam.ui.screens.myLibraryScreen

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.data.repository.UniversityRepository
import com.example.finalexam.entity.University
import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.intent.UniversityIntent
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.ui.theme.AppColors
import com.example.finalexam.usecase.university.AddSubjectUseCase
import com.example.finalexam.usecase.university.GetAllUniversitiesUseCase
import com.example.finalexam.usecase.upload.UploadDocumentsUseCase
import com.example.finalexam.viewmodel.UniversityViewModel
import com.example.finalexam.viewmodel.UniversityViewModelFactory
import com.example.finalexam.viewmodel.UploadDocumentViewModel
import com.example.finalexam.viewmodel.UploadDocumentViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadDocumentScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onUploadClick: (UploadDocument?) -> Unit = {},
    //===Phần này của Hảo 22/6===
    onUploadSuccess: () -> Unit // Callback khi upload thành công
    //===Phần này của Hảo 22/6===
) {
    val context = LocalContext.current

    // ===== KHỞI TẠO VIEWMODELS =====
    // Tạo các UseCase dependencies cho UniversityViewModel
    val repository = remember { UniversityRepository() }
    val getAllUniversitiesUseCase = remember { GetAllUniversitiesUseCase(repository) }
    val addSubjectUseCase = remember { AddSubjectUseCase(repository) }
    
    // UniversityViewModel để quản lý danh sách trường và môn học
    val universityViewModel: UniversityViewModel = viewModel(
        factory = remember {
            UniversityViewModelFactory(getAllUniversitiesUseCase, addSubjectUseCase)
        }
    )

    // Tạo UseCase cho UploadDocumentViewModel
    val uploadDocumentsUseCase = remember { UploadDocumentsUseCase() }
    
    // UploadDocumentViewModel với dependency injection từ universityViewModel
    val uploadDocumentViewModel: UploadDocumentViewModel = viewModel(
        factory = remember {
            UploadDocumentViewModelFactory(universityViewModel, uploadDocumentsUseCase)
        }
    )


    // ===== COLLECT STATES =====
    val uploadState by uploadDocumentViewModel.state.collectAsState()
    val universityState by universityViewModel.state.collectAsState()

    // ===== FILE PICKER LAUNCHER =====
    // Launcher để chọn file từ thiết bị
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            //===Phần này của Hảo 22/6===
            // Tạo UploadDocument entity từ URI được chọn với Uri thực
            val document = UploadDocument(
                id = UUID.randomUUID().toString(),
                name = getFileName(context, uri),
                fileUrl = uri.toString(),
                uri = uri // Lưu Uri thực để upload
            )
            //===Phần này của Hảo 22/6===
            // Gửi intent để set document đã chọn
            uploadDocumentViewModel.processIntent(UploadDocumentIntent.SetSelectedDocument(document))
        }
    }

    // ===== SNACKBAR STATE =====
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // ===== LAUNCHED EFFECTS =====
    // Load danh sách universities khi screen được khởi tạo
    LaunchedEffect(Unit) {
        universityViewModel.processIntent(UniversityIntent.LoadUniversities)
    }

    //===Phần này của Hảo 22/6===
    // Set context cho ViewModel để upload file
    LaunchedEffect(Unit) {
        uploadDocumentViewModel.setContext(context)
    }
    //===Phần này của Hảo 22/6===

    // Hiển thị snackbar khi có lỗi từ university
    LaunchedEffect(universityState.error) {
        universityState.error?.let { err ->
            if (err.contains("tồn tại")) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(err)
                }
            }
        }
    }

    //===Phần này của Hảo 22/6===
    // Hiển thị snackbar khi có lỗi từ upload
    LaunchedEffect(uploadState.error) {
        uploadState.error?.let { error ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(error)
            }
        }
    }

    // Xử lý upload thành công
    LaunchedEffect(uploadState.uploadSuccess) {
        if (uploadState.uploadSuccess) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Upload thành công!")
                //===Phần này của Hảo 22/6===
                // Chuyển về MyLibrary sau khi upload thành công
                delay(1000) // Đợi 1 giây để user thấy snackbar
                onUploadSuccess() // Gọi callback để navigate về MyLibrary
                //===Phần này của Hảo 22/6===
            }
        }
    }
    //===Phần này của Hảo 22/6===

    // ===== UI LAYOUT =====
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // ===== TOP APP BAR =====
        TopAppBar(
            title = { Text("Studocu") },
            navigationIcon = {
                IconButton(onClick = {
                    // Gửi intent Back và gọi callback
                    uploadDocumentViewModel.processIntent(UploadDocumentIntent.Back)
                    onBackClick()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        // ===== MAIN CONTENT =====
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // ===== TITLE INPUT SECTION =====
            OutlinedTextField(
                value = uploadState.title,
                onValueChange = {
                    // Gửi intent để cập nhật title
                    uploadDocumentViewModel.processIntent(UploadDocumentIntent.SetTitle(it))
                },
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // ===== DESCRIPTION INPUT SECTION =====
            OutlinedTextField(
                value = uploadState.description,
                onValueChange = {
                    // Gửi intent để cập nhật description
                    uploadDocumentViewModel.processIntent(UploadDocumentIntent.SetDescription(it))
                },
                label = { Text("Mô tả") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(Modifier.height(16.dp))

            // ===== FILE SELECTION BUTTON =====
            Button(
                onClick = { launcher.launch("*/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uploadState.selectedDocument == null) "Chọn tài liệu từ máy" else "Chọn tài liệu khác")
            }

            Spacer(Modifier.height(16.dp))

            // ===== SELECTED FILE DISPLAY =====
            uploadState.selectedDocument?.let { doc ->
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
                            // Gửi intent để xóa document đã chọn
                            uploadDocumentViewModel.processIntent(UploadDocumentIntent.RemoveSelectedDocument)
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Bỏ tài liệu")
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // ===== UNIVERSITY & SUBJECT SELECTION SECTION =====
            if (universityState.universityList.isNotEmpty()) {
                UniversitySelectionSection(
                    university = universityState.selectedUniversity ?: universityState.universityList.first(),
                    universityList = universityState.universityList,
                    onUniversitySelected = { id ->
                        // Gửi intent để chọn university
                        val selectedUni = universityState.universityList.find { it.id == id }
                        selectedUni?.let { uni ->
                            universityViewModel.processIntent(UniversityIntent.SelectUniversity(id))
                        }
                    },
                    onSubjectSelected = { index ->
                        // Gửi intent để chọn subject
                        universityState.selectedUniversity?.let { uni ->
                            val updatedUni = uni.copy(selectedSubjectIndex = index)
                            universityViewModel.processIntent(UniversityIntent.SelectSubject(uni.id, index))
                        }
                    },
                    onAddSubject = { name ->
                        // Gửi intent để thêm subject mới
                        universityState.selectedUniversity?.let { uni ->
                            universityViewModel.processIntent(UniversityIntent.AddSubject(uni.id, name))
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(24.dp))

            //===Phần này của Hảo 22/6===
            // Debug info để kiểm tra trạng thái
            Text(
                text = "Debug: isUploading=${uploadState.isUploading}, error=${uploadState.error}, success=${uploadState.uploadSuccess}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            // Debug info để kiểm tra trạng thái university
            Text(
                text = "University Debug: isLoading=${universityState.isLoading}, count=${universityState.universityList.size}, error=${universityState.error}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            
            // Debug info để kiểm tra file đã chọn
            Text(
                text = "File Debug: selected=${uploadState.selectedDocument?.name ?: "None"}, uri=${uploadState.selectedDocument?.uri != null}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            //===Phần này của Hảo 22/6===

            // ===== UPLOAD BUTTON =====
            Button(
                onClick = {
                    uploadDocumentViewModel.processIntent(UploadDocumentIntent.Upload)
                    onUploadClick(uploadState.selectedDocument)
                },
                enabled = true, // Luôn enable để test
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = Color.White
                )
            ) {
                if (uploadState.isUploading) {
                    // Hiển thị loading indicator khi đang upload
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Upload Document")
                }
            }
        }

        // ===== SNACKBAR HOST =====
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

//    @Preview(showBackground = true)
//    @Composable
//    fun UploadDocumentScreenPreview() {
//        UploadDocumentScreen()
//    }
}

