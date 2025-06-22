package com.example.finalexam.data.utils

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtil {
    /**
     * Copy file từ content://... sang file tạm trong cache và trả về file tạm đó
     */
    fun getFileFromUri(context: Context, uri: Uri, fileName: String): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", fileName, context.cacheDir)
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }
} 