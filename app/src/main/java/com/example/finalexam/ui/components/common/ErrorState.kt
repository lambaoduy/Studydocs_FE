package com.example.finalexam.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Common error state component for the entire app
 *
 * @param message Error message to display
 * @param onRetry Callback when retry button is clicked
 * @param title Optional title (default "Có lỗi xảy ra")
 * @param icon Icon to display (default ErrorOutline)
 * @param retryText Text for retry button (default "Thử lại")
 * @param showRetryIcon Show refresh icon in retry button (default true)
 * @param iconSize Size of the error icon (default 80dp)
 * @param iconTint Color of the error icon (default error color)
 * @param titleColor Color of the title text (default onSurface)
 * @param messageColor Color of the message text (default onSurfaceVariant)
 * @param contentPadding Padding around the content (default 32dp)
 * @param useOutlinedButton Use outlined button instead of filled (default false)
 * @param modifier Modifier for the container
 */
@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Có lỗi xảy ra",
    icon: ImageVector = Icons.Outlined.ErrorOutline,
    retryText: String = "Thử lại",
    showRetryIcon: Boolean = true,
    iconSize: Dp = 80.dp,
    iconTint: Color = MaterialTheme.colorScheme.error,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    messageColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    contentPadding: Dp = 32.dp,
    useOutlinedButton: Boolean = false
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = iconTint
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = titleColor,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = messageColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (useOutlinedButton) {
                OutlinedButton(onClick = onRetry) {
                    RetryButtonContent(showRetryIcon, retryText)
                }
            } else {
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    RetryButtonContent(showRetryIcon, retryText)
                }
            }
        }
    }
}

@Composable
private fun RetryButtonContent(
    showIcon: Boolean,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showIcon) {
            Icon(
                Icons.Outlined.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text)
    }
}

// Predefined error states for common use cases
object ErrorStates {

    @Composable
    fun NetworkError(
        onRetry: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        ErrorState(
            icon = Icons.Outlined.WifiOff,
            title = "Không có kết nối mạng",
            message = "Vui lòng kiểm tra kết nối internet và thử lại",
            onRetry = onRetry,
            modifier = modifier
        )
    }

    @Composable
    fun ServerError(
        onRetry: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        ErrorState(
            icon = Icons.Outlined.CloudOff,
            title = "Lỗi máy chủ",
            message = "Máy chủ đang gặp sự cố, vui lòng thử lại sau",
            onRetry = onRetry,
            modifier = modifier
        )
    }

    @Composable
    fun LoadDataError(
        message: String = "Không thể tải dữ liệu",
        onRetry: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        ErrorState(
            title = "Tải dữ liệu thất bại",
            message = message,
            onRetry = onRetry,
            modifier = modifier
        )
    }

    @Composable
    fun GenericError(
        message: String,
        onRetry: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        ErrorState(
            message = message,
            onRetry = onRetry,
            useOutlinedButton = true,
            modifier = modifier
        )
    }
}