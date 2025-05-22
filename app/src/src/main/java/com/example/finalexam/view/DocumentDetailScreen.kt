package com.example.finalexam.view

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.viewmodel.DocumentViewModel
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

@Composable
fun DocumentDetailScreen(documentId: String, userId: String = "currentUserId") {
    val viewModel: DocumentViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Yêu cầu quyền lưu trữ
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted && state.downloadUrl != null) {
            downloadFile(context, state.downloadUrl!!, state.document?.title ?: "document.pdf")
        }
    }

    // Tải tài liệu khi vào màn hình
    LaunchedEffect(Unit) {
        viewModel.processIntent(DocumentIntent.LoadDocument(documentId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.document?.title ?: "Loading...") },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.document != null) {
                // Hiển thị PDF
                state.document?.fileUrl?.let { url ->
                    PDFView(
                        context,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ).fromUri(Uri.parse(url)).load()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nút Download
                Button(onClick = {
                    viewModel.processIntent(DocumentIntent.DownloadDocument(documentId))
                    state.downloadUrl?.let { url ->
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            downloadFile(context, url, state.document?.title ?: "document.pdf")
                        } else {
                            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                    }
                }) {
                    Text("Download")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Nút Like/Unlike
                IconButton(onClick = {
                    if (state.isLiked) {
                        viewModel.processIntent(DocumentIntent.UnlikeDocument(documentId, userId))
                    } else {
                        viewModel.processIntent(DocumentIntent.LikeDocument(documentId, userId))
                    }
                }) {
                    Icon(
                        imageVector = if (state.isLiked) Icons.Default.ThumbUp else Icons.Default.ThumbUpOffAlt,
                        contentDescription = "Like"
                    )
                }

                // Nút Bookmark/Unbookmark
                IconButton(onClick = {
                    if (state.isBookmarked) {
                        viewModel.processIntent(DocumentIntent.UnbookmarkDocument(documentId, userId))
                    } else {
                        viewModel.processIntent(DocumentIntent.BookmarkDocument(documentId, userId))
                    }
                }) {
                    Icon(
                        imageVector = if (state.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark"
                    )
                }

                state.errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colors.error)
                }
            }
        }
    }
}

/**
 * Tải file từ URL về thiết bị
 * @param context Context của ứng dụng
 * @param url URL của file cần tải
 * @param fileName Tên file khi lưu
 */
fun downloadFile(context: Context, url: String, fileName: String) {
    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle(fileName)
        .setDescription("Downloading")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}