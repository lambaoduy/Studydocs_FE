package com.example.finalexam.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.viewmodel.DocumentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailScreen(documentId: String, userId: String = "currentUserId") {
    val viewModel: DocumentViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // State for PDF rendering
    var pdfRenderer by remember { mutableStateOf<PdfRenderer?>(null) }
    var currentPage by remember { mutableStateOf(0) }
    var pageCount by remember { mutableStateOf(0) }
    var pdfBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Permission launcher for storage
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted && state.downloadUrl != null) {
            downloadFile(context, state.downloadUrl!!, state.document?.title ?: "document.pdf")
        }
    }

    // Load document when screen is created
    LaunchedEffect(Unit) {
        viewModel.processIntent(DocumentIntent.LoadDocument(documentId))
    }

    // Download and render PDF when fileUrl or downloadUrl changes
    LaunchedEffect(state.document?.fileUrl, state.downloadUrl) {
        val url = state.downloadUrl ?: state.document?.fileUrl
        if (url != null) {
            try {
                val file = withContext(Dispatchers.IO) {
                    val tempFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "temp.pdf")
                    URL(url).openStream().use { input ->
                        FileOutputStream(tempFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    tempFile
                }
                val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                pdfRenderer = PdfRenderer(fileDescriptor)
                pageCount = pdfRenderer?.pageCount ?: 0
                if (pageCount > 0) {
                    pdfRenderer?.openPage(currentPage)?.let { page ->
                        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                        pdfBitmap = bitmap
                        page.close()
                    }
                }
            } catch (e: Exception) {
                viewModel.processIntent(DocumentIntent.Error("Failed to render PDF: ${e.message}"))
            }
        }
    }

    // Cleanup PdfRenderer when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            pdfRenderer?.close()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.document?.title ?: "Loading...") },
                backgroundColor = MaterialTheme.colorScheme.primary
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
            } else if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (pdfBitmap != null) {
                // Display PDF page
                Image(
                    bitmap = pdfBitmap!!.asImageBitmap(),
                    contentDescription = "PDF page ${currentPage + 1}",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                // Pagination controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (currentPage > 0) {
                                currentPage--
                                pdfRenderer?.openPage(currentPage)?.let { page ->
                                    val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                    pdfBitmap = bitmap
                                    page.close()
                                }
                            }
                        },
                        enabled = currentPage > 0
                    ) {
                        Text("Previous")
                    }
                    Text(
                        text = "Page ${currentPage + 1} of $pageCount",
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Button(
                        onClick = {
                            if (currentPage < pageCount - 1) {
                                currentPage++
                                pdfRenderer?.openPage(currentPage)?.let { page ->
                                    val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                    pdfBitmap = bitmap
                                    page.close()
                                }
                            }
                        },
                        enabled = currentPage < pageCount - 1
                    ) {
                        Text("Next")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Download button
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

                // Like/Unlike button
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
            }
        }
    }
}

/**
 * Download file from URL to device
 * @param context Application context
 * @param url URL of the file to download
 * @param fileName Name of the file when saved
 */
fun downloadFile(context: Context, url: String, fileName: String) {
    val request = android.app.DownloadManager.Request(Uri.parse(url))
        .setTitle(fileName)
        .setDescription("Downloading")
        .setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as android.app.DownloadManager
    downloadManager.enqueue(request)
}