package com.example.finalexam.ui.document

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.viewmodel.DocumentViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

fun downloadFile(context: Context, url: String, fileName: String) {
    try {
        val request = android.app.DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading")
            .setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as android.app.DownloadManager
        downloadManager.enqueue(request)
        Toast.makeText(context, "Đang tải $fileName...", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Lỗi tải tệp: ${e.message}", Toast.LENGTH_LONG).show()
        Log.e("DocumentDetail", "Download error: ${e.message}", e)
    }
}

fun Modifier.boxedShadow(): Modifier = this.then(
    Modifier
        .background(Color.White, RoundedCornerShape(8.dp))
        .padding(4.dp)
        .background(Color.White, RoundedCornerShape(8.dp))
        .shadow(
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailScreen(documentId: String, onNavigateBack: () -> Unit) {
    val viewModel: DocumentViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val storage = FirebaseStorage.getInstance()

    // State for file rendering
    var pdfRenderer by remember { mutableStateOf<PdfRenderer?>(null) }
    var currentPage by remember { mutableStateOf(0) }
    var pageCount by remember { mutableStateOf(0) }
    var fileBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Permission launcher for storage
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted && state.downloadUrl != null) {
            val fileName = "${state.document?.title ?: "document"}.${state.document?.fileUrl?.substringAfterLast(".") ?: "file"}"
            downloadFile(context, state.downloadUrl!!, fileName)
            Log.d("DocumentDetail", "Tải file thành công: ${state.downloadUrl}")
        } else {
            Toast.makeText(context, "Quyền truy cập bộ nhớ bị từ chối", Toast.LENGTH_SHORT).show()
            Log.d("DocumentDetail", "Storage permission denied")
        }
    }

    // Log documentId and state for debugging
    LaunchedEffect(documentId) {
        Log.d("DocumentDetail", "Document ID: $documentId")
        Log.d("DocumentDetail", "State: document=${state.document?.fileUrl}, downloadUrl=${state.downloadUrl}, error=${state.errorMessage}, isLoading=${state.isLoading}")
    }

    // Load document when screen is created
    LaunchedEffect(Unit) {
        viewModel.processIntent(DocumentIntent.LoadDocument(documentId))
    }

    // Get download URL from Firebase Storage
    LaunchedEffect(state.document?.fileUrl) {
        if (state.document?.fileUrl == null) {
            Log.d("DocumentDetail", "fileUrl is null")
            viewModel.processIntent(DocumentIntent.Error("Không tìm thấy đường dẫn file trong cơ sở dữ liệu"))
            return@LaunchedEffect
        }
        val filePath = state.document?.fileUrl?.removePrefix("/") ?: return@LaunchedEffect
        Log.d("DocumentDetail", "Attempting to fetch URL for: $filePath")
        try {
            val storageRef = storage.reference.child(filePath)
            val downloadUrl = storageRef.downloadUrl.await().toString()
            Log.d("DocumentDetail", "Success: Download URL = $downloadUrl")
            viewModel.processIntent(DocumentIntent.DownloadDocument(downloadUrl))
        } catch (e: Exception) {
            Log.e("DocumentDetail", "Failed to get downloadUrl: ${e.message}", e)
            viewModel.processIntent(DocumentIntent.Error("Lỗi lấy URL tải xuống: ${e.message}"))
        }
    }

    // Download and render file when downloadUrl changes
    LaunchedEffect(state.downloadUrl) {
        val url = state.downloadUrl
        if (url != null) {
            try {
                Log.d("DocumentDetail", "Starting download for URL: $url")
                val file = withContext(Dispatchers.IO) {
                    val uri = Uri.parse(url)
                    val fileName = uri.lastPathSegment ?: "temp_file"
                    val tempFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
                    Log.d("DocumentDetail", "Creating temp file at: ${tempFile.absolutePath}")
                    try {
                        URL(url).openStream().use { input ->
                            FileOutputStream(tempFile).use { output ->
                                input.copyTo(output)
                            }
                        }
                        if (tempFile.length() == 0L) {
                            Log.e("DocumentDetail", "Downloaded file is empty")
                            throw Exception("File downloaded is empty")
                        }
                        Log.d("DocumentDetail", "File downloaded, size: ${tempFile.length()} bytes")
                        tempFile
                    } catch (e: Exception) {
                        Log.e("DocumentDetail", "Error downloading file: ${e.message}", e)
                        throw e
                    }
                }
                Log.d("DocumentDetail", "Downloaded file: ${file.absolutePath}, extension: ${file.name.substringAfterLast(".")}")

                when (file.name.substringAfterLast(".").lowercase()) {
                    "pdf" -> {
                        try {
                            Log.d("DocumentDetail", "Attempting to open PDF: ${file.absolutePath}")
                            val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                            pdfRenderer = PdfRenderer(fileDescriptor)
                            pageCount = pdfRenderer?.pageCount ?: 0
                            Log.d("DocumentDetail", "PDF page count: $pageCount")
                            if (pageCount > 0) {
                                pdfRenderer?.openPage(currentPage)?.let { page ->
                                    val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                    fileBitmap = bitmap
                                    page.close()
                                    Log.d("DocumentDetail", "Rendered PDF page $currentPage")
                                }
                            } else {
                                Log.e("DocumentDetail", "PDF has no pages")
                                viewModel.processIntent(DocumentIntent.Error("PDF không có trang"))
                            }
                        } catch (e: Exception) {
                            Log.e("DocumentDetail", "Error rendering PDF: ${e.message}", e)
                            viewModel.processIntent(DocumentIntent.Error("Lỗi render PDF: ${e.message}"))
                        }
                    }
                    "jpg", "jpeg", "png" -> {
                        try {
                            val bitmap = withContext(Dispatchers.IO) {
                                BitmapFactory.decodeFile(file.absolutePath)
                            }
                            if (bitmap != null) {
                                fileBitmap = bitmap
                                Log.d("DocumentDetail", "Rendered image file: ${file.absolutePath}")
                            } else {
                                Log.e("DocumentDetail", "Failed to decode image file: ${file.absolutePath}")
                                viewModel.processIntent(DocumentIntent.Error("Không thể giải mã file hình ảnh"))
                            }
                        } catch (e: Exception) {
                            Log.e("DocumentDetail", "Error decoding image: ${e.message}", e)
                            viewModel.processIntent(DocumentIntent.Error("Lỗi giải mã hình ảnh: ${e.message}"))
                        }
                    }
                    else -> {
                        Log.e("DocumentDetail", "Unsupported file format: ${file.name.substringAfterLast(".")}")
                        viewModel.processIntent(DocumentIntent.Error("Định dạng file không được hỗ trợ: ${file.name.substringAfterLast(".")}"))
                    }
                }
            } catch (e: Exception) {
                Log.e("DocumentDetail", "Error downloading or rendering file: ${e.message}", e)
                viewModel.processIntent(DocumentIntent.Error("Lỗi tải hoặc render file: ${e.message}"))
            }
        } else {
            Log.d("DocumentDetail", "Download URL is null")
        }
    }

    // Cleanup PdfRenderer when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            pdfRenderer?.close()
            Log.d("DocumentDetail", "PdfRenderer closed")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.document?.title ?: "Loading...") },
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
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.errorMessage != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            } else {
                if (fileBitmap != null) {
                    Image(
                        bitmap = fileBitmap!!.asImageBitmap(),
                        contentDescription = if (state.document?.fileUrl?.endsWith(".pdf") == true) {
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

                // Action bar with Download and Like/Unlike
                if (state.downloadUrl != null && state.document != null) {
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
                            IconButton(onClick = {
                                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    val fileName = "${state.document?.title ?: "document"}.${state.document?.fileUrl?.substringAfterLast(".") ?: "file"}"
                                    downloadFile(context, state.downloadUrl!!, fileName)
                                } else {
                                    permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.GetApp,
                                    contentDescription = "Download",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = {
                                if (state.isLiked) {
                                    viewModel.processIntent(DocumentIntent.UnlikeDocument(documentId))
                                } else {
                                    viewModel.processIntent(DocumentIntent.LikeDocument(documentId))
                                }
                            }) {
                                Icon(
                                    imageVector = if (state.isLiked) Icons.Default.ThumbUp else Icons.Default.ThumbUpOffAlt,
                                    contentDescription = "Like",
                                    tint = if (state.isLiked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                // Pagination controls for PDF
                if (state.document?.fileUrl?.endsWith(".pdf") == true && pageCount > 1) {
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
                                    try {
                                        pdfRenderer?.openPage(currentPage)?.let { page ->
                                            val bitmap = Bitmap.createBitmap(
                                                page.width,
                                                page.height,
                                                Bitmap.Config.ARGB_8888
                                            )
                                            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                            fileBitmap = bitmap
                                            page.close()
                                            Log.d("DocumentDetail", "Switched to PDF page $currentPage")
                                        } ?: run {
                                            Log.e("DocumentDetail", "Failed to open page $currentPage")
                                            viewModel.processIntent(DocumentIntent.Error("Không thể mở trang $currentPage"))
                                        }
                                    } catch (e: Exception) {
                                        Log.e("DocumentDetail", "Error rendering page $currentPage: ${e.message}", e)
                                        viewModel.processIntent(DocumentIntent.Error("Lỗi render trang $currentPage: ${e.message}"))
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
                                    try {
                                        pdfRenderer?.openPage(currentPage)?.let { page ->
                                            val bitmap = Bitmap.createBitmap(
                                                page.width,
                                                page.height,
                                                Bitmap.Config.ARGB_8888
                                            )
                                            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                            fileBitmap = bitmap
                                            page.close()
                                            Log.d("DocumentDetail", "Switched to PDF page $currentPage")
                                        } ?: run {
                                            Log.e("DocumentDetail", "Failed to open page $currentPage")
                                            viewModel.processIntent(DocumentIntent.Error("Không thể mở trang $currentPage"))
                                        }
                                    } catch (e: Exception) {
                                        Log.e("DocumentDetail", "Error rendering page $currentPage: ${e.message}", e)
                                        viewModel.processIntent(DocumentIntent.Error("Lỗi render trang $currentPage: ${e.message}"))
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