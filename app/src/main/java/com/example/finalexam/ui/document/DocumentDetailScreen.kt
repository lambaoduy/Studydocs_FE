package com.example.finalexam.ui.document

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.viewmodel.DocumentViewModel
import com.example.finalexam.viewmodel.FollowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

//fun downloadFile(context: Context, url: String, fileName: String) {
//    try {
//        val request = android.app.DownloadManager.Request(Uri.parse(url))
//            .setTitle(fileName)
//            .setDescription("Downloading")
//            .setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            .setDestinationInExternalPublicDir(android.os.Environment.DIRECTORY_DOWNLOADS, fileName)
//            .setAllowedOverMetered(true)
//            .setAllowedOverRoaming(true)
//        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as android.app.DownloadManager
//        downloadManager.enqueue(request)
//        Toast.makeText(context, "Đang tải $fileName...", Toast.LENGTH_SHORT).show()
//    } catch (e: Exception) {
//        Toast.makeText(context, "Lỗi tải tệp: ${e.message}", Toast.LENGTH_LONG).show()
//        Log.e("DocumentDetail", "Download error: ${e.message}", e)
//    }
//}

suspend fun downloadFileWithSAF(context: Context, url: String, fileName: String, uri: Uri) {
    try {
        withContext(Dispatchers.IO) {
            val tempFile = File(context.cacheDir, "temp_$fileName")
            URL(url).openStream().use { inputStream ->
                FileOutputStream(tempFile).use { tempOutput ->
                    inputStream.copyTo(tempOutput)
                }
                if (tempFile.length() == 0L) {
                    throw Exception("Temp file is empty")
                }
                tempFile.inputStream().use { tempInput ->
                    context.contentResolver.openOutputStream(uri)?.use { output ->
                        tempInput.copyTo(output)
                    }
                }
            }
        }
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Đã lưu $fileName thành công!", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Lỗi lưu file: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}

fun getMimeType(fileName: String): String {
    return when (fileName.substringAfterLast(".", "").lowercase()) {
        "pdf" -> "application/pdf"
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "txt" -> "text/plain"
        else -> "*/*"
    }
}

fun Modifier.boxedShadow(): Modifier = this.then(
    Modifier
        .background(Color.White, RoundedCornerShape(8.dp))
        .padding(4.dp)
        .background(Color.White, RoundedCornerShape(8.dp))
        .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
)

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailScreen(
    documentId: String,
    onNavigateBack: () -> Unit
) {
    val documentViewModel: DocumentViewModel = viewModel()
    val followViewModel: FollowViewModel = viewModel()
    val documentState by documentViewModel.state.collectAsState()
    val followState by followViewModel.state.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
// Thêm vào sau các biến như documentState, followState
    val currentUserId by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser?.uid) }
    val isCurrentUser by derivedStateOf {
        currentUserId != null && currentUserId == documentState.document?.userId
    }
    val documentApi: DocumentApi = RetrofitClient.createApi(DocumentApi::class.java)

    var pdfRenderer by remember { mutableStateOf<PdfRenderer?>(null) }
    var currentPage by remember { mutableStateOf(0) }
    var pageCount by remember { mutableStateOf(0) }
    var fileBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var tempFile by remember { mutableStateOf<File?>(null) }

    // Tính toán isFollowed dựa trên followState
    val isFollowed by derivedStateOf {
        val currentUserId = documentState.document?.userId
        currentUserId != null && followState.followings.any {
            it.targetId == currentUserId && it.targetType == FollowType.USER
        }
    }

    val storageAccessLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument(getMimeType(documentState.document?.fileUrl ?: ""))
    ) { uri ->
        if (uri != null && documentState.downloadUrl != null) {
            val fileName = "${documentState.document?.title ?: "document"}.${documentState.document?.fileUrl?.substringAfterLast(".") ?: "pdf"}"
            try {
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                scope.launch {
                    if (tempFile != null && tempFile!!.exists()) {
                        tempFile!!.inputStream().use { tempInput ->
                            context.contentResolver.openOutputStream(uri)?.use { output ->
                                tempInput.copyTo(output)
                            }
                        }
                        Toast.makeText(context, "Đã lưu $fileName thành công!", Toast.LENGTH_SHORT).show()
                    } else {
                        downloadFileWithSAF(context, documentState.downloadUrl!!, fileName, uri)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Lỗi khi lưu file: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Không thể lưu file", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        documentViewModel.processIntent(DocumentIntent.LoadDocument(documentId))
        followViewModel.processIntent(FollowIntent.GetFollowings)
    }

//    LaunchedEffect(documentState.document?.fileUrl) {
//        if (documentState.document?.fileUrl == null) {
//            documentViewModel.processIntent(DocumentIntent.Error("Không tìm thấy đường dẫn file trong cơ sở dữ liệu"))
//            return@LaunchedEffect
//        }
//        try {
//            val response = documentApi.getDownloadUrl(documentId)
//            if (response.isSuccessful && response.body()?.data != null) {
//                documentViewModel.processIntent(DocumentIntent.DownloadDocument(response.body()!!.data))
//            } else {
//                documentViewModel.processIntent(DocumentIntent.Error("Lỗi lấy URL tải xuống: ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            documentViewModel.processIntent(DocumentIntent.Error("Lỗi: ${e.message}"))
//        }
//    }

    LaunchedEffect(documentState.downloadUrl) {
        val url = documentState.downloadUrl
        if (url != null) {
            try {
                tempFile = withContext(Dispatchers.IO) {
                    val uri = Uri.parse(url)
                    val fileName = uri.lastPathSegment ?: "temp_file_${System.currentTimeMillis()}"
                    val file = File(context.cacheDir, fileName)
                    URL(url).openStream().use { input ->
                        FileOutputStream(file).use { output ->
                            input.copyTo(output)
                        }
                    }
                    if (file.length() == 0L) {
                        throw Exception("File downloaded is empty")
                    }
                    file
                }
                when (getMimeType(tempFile!!.name)) {
                    "application/pdf" -> {
                        val fileDescriptor = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
                        pdfRenderer = PdfRenderer(fileDescriptor)
                        pageCount = pdfRenderer?.pageCount ?: 0
                        if (pageCount > 0) {
                            pdfRenderer?.openPage(currentPage)?.let { page ->
                                val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                fileBitmap = bitmap
                                page.close()
                            }
                        } else {
                            documentViewModel.processIntent(DocumentIntent.Error("PDF không có trang"))
                        }
                    }
                    "image/jpeg", "image/png" -> {
                        fileBitmap = BitmapFactory.decodeFile(tempFile!!.absolutePath)
                        if (fileBitmap == null) {
                            documentViewModel.processIntent(DocumentIntent.Error("Không thể giải mã file hình ảnh"))
                        }
                    }
                    else -> {
                        documentViewModel.processIntent(DocumentIntent.Error("Định dạng file không được hỗ trợ: ${tempFile!!.name.substringAfterLast(".")}"))
                    }
                }
            } catch (e: Exception) {
                documentViewModel.processIntent(DocumentIntent.Error("Lỗi tải hoặc render file: ${e.message}"))
            }
        }
    }

    LaunchedEffect(documentState.errorMessage) {
        if (!documentState.errorMessage.isNullOrEmpty()) {
            Toast.makeText(context, documentState.errorMessage, Toast.LENGTH_LONG).show()
            documentViewModel.processIntent(DocumentIntent.Error(""))
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            pdfRenderer?.close()
            tempFile?.delete()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(documentState.document?.title ?: "Loading...") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (documentState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (documentState.errorMessage != null && documentState.errorMessage!!.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = documentState.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            } else {
                if (fileBitmap != null) {
                    Image(
                        bitmap = fileBitmap!!.asImageBitmap(),
                        contentDescription = if (documentState.document?.fileUrl?.endsWith(".pdf") == true) {
                            "PDF page ${currentPage + 1}"
                        } else {
                            "Image file"
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.95f)
                            .padding(vertical = 8.dp)
                            .boxedShadow()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Đang tải tài liệu...",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp
                        )
                    }
                }

                if (documentState.downloadUrl != null && documentState.document != null) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        tonalElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var isFollowProcessing by remember { mutableStateOf(false) }
                            IconButton(
                                onClick = {
                                    val userId = documentState.document?.userId
                                    if (userId.isNullOrEmpty()) {
                                        Toast.makeText(context, "Không tìm thấy ID người dùng", Toast.LENGTH_SHORT).show()
                                        return@IconButton
                                    }
                                    if (isFollowProcessing) {
                                        return@IconButton
                                    }
                                    scope.launch {
                                        isFollowProcessing = true
                                        try {
                                            val wasFollowed = isFollowed
                                            documentViewModel.processIntent(
                                                if (isFollowed) {
                                                    DocumentIntent.UnFollow(userId, FollowType.USER) // userId là targetId
                                                } else {
                                                    DocumentIntent.Follow(userId, FollowType.USER)
                                                }
                                            )
                                            kotlinx.coroutines.delay(500)
                                            if (followState.errorMessage.isNullOrEmpty()) {
                                                Toast.makeText(
                                                    context,
                                                    if (wasFollowed) "Đã bỏ theo dõi" else "Đã theo dõi",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    followState.errorMessage ?: "Lỗi xử lý theo dõi",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } catch (e: Exception) {
                                            Log.e("DocumentDetailScreen", "Lỗi xử lý follow/unfollow: ${e.message}")
                                            documentViewModel.processIntent(DocumentIntent.Error("Lỗi xử lý theo dõi: ${e.message}"))
                                        } finally {
                                            isFollowProcessing = false
                                        }
                                    }
                                },
                                enabled = !isFollowProcessing && documentState.document?.userId?.isNotEmpty() == true && !documentState.isLoading
                            ) {
                                if (isFollowProcessing) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (isFollowed) Icons.Default.PersonRemove else Icons.Default.PersonAdd,
                                        contentDescription = if (isFollowed) "Bỏ theo dõi" else "Theo dõi",
                                        tint = if (isFollowed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

//                            IconButton(onClick = {
//                                val fileName = "${documentState.document?.title ?: "document"}.${documentState.document?.fileUrl?.substringAfterLast(".") ?: "pdf"}"
//                                downloadFile(context, documentState.downloadUrl!!, fileName)
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.GetApp,
//                                    contentDescription = "Download with DownloadManager",
//                                    tint = MaterialTheme.colorScheme.primary
//                                )
//                            }

//                            IconButton(onClick = {
//                                val fileName = "${documentState.document?.title ?: "document"}.${documentState.document?.fileUrl?.substringAfterLast(".") ?: "pdf"}"
//                                downloadFile(context, documentState.downloadUrl!!, fileName)
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.GetApp,
//                                    contentDescription = "Download with DownloadManager",
//                                    tint = MaterialTheme.colorScheme.primary
//                                )
//                            }

                            IconButton(onClick = {
                                val fileName = "${documentState.document?.title ?: "document"}.${documentState.document?.fileUrl?.substringAfterLast(".") ?: "pdf"}"
                                storageAccessLauncher.launch(fileName)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.SaveAlt,
                                    contentDescription = "Save with SAF",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            IconButton(onClick = {
                                scope.launch {
                                    try {
                                        if (documentState.isLiked) {
                                            documentViewModel.processIntent(DocumentIntent.UnlikeDocument(documentId))
                                            if (documentState.errorMessage.isNullOrEmpty()) {
                                                Toast.makeText(context, "Đã bỏ thích", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            documentViewModel.processIntent(DocumentIntent.LikeDocument(documentId))
                                            if (documentState.errorMessage.isNullOrEmpty()) {
                                                Toast.makeText(context, "Đã thích", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Log.e("DocumentDetail", "Error processing Like/Unlike: ${e.message}", e)
                                        documentViewModel.processIntent(DocumentIntent.Error("Lỗi: ${e.message}"))
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = if (documentState.isLiked) Icons.Default.ThumbUp else Icons.Default.ThumbUpOffAlt,
                                    contentDescription = if (documentState.isLiked) "Unlike" else "Like",
                                    tint = if (documentState.isLiked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                if (documentState.document?.fileUrl?.endsWith(".pdf") == true && pageCount > 1) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                if (currentPage > 0) {
                                    currentPage--
                                    pdfRenderer?.openPage(currentPage)?.let { page ->
                                        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                                        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                        fileBitmap = bitmap
                                        page.close()
                                    }
                                }
                            },
                            enabled = currentPage > 0,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Previous")
                        }
                        Text(
                            text = "Page ${currentPage + 1} of $pageCount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        OutlinedButton(
                            onClick = {
                                if (currentPage < pageCount - 1) {
                                    currentPage++
                                    pdfRenderer?.openPage(currentPage)?.let { page ->
                                        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                                        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                        fileBitmap = bitmap
                                        page.close()
                                    }
                                }
                            },
                            enabled = currentPage < pageCount - 1,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Next")
                        }
                    }
                }
            }
        }
    }
}