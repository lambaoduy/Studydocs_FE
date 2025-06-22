package com.example.finalexam.utils

import android.content.Context
import android.net.Uri
import java.io.File

//===Phần này của Hảo 22/6===
/**
 * Utility class để xử lý file operations
 * Chuyển đổi Uri (content://...) thành File thực để upload
 */
object FileUtil {
    /**
     * Copy file từ content://... sang file tạm trong cache và trả về file tạm đó
     * 
     * @param context Context để truy cập ContentResolver
     * @param uri Uri của file (thường là content://...)
     * @param fileName Tên file
     * @return File tạm trong cache directory
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
//===Phần này của Hảo 22/6=== 