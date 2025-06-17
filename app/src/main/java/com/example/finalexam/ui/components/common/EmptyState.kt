package com.example.finalexam.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
 * Common empty state component for the entire app
 *
 * @param icon The icon to display
 * @param title Primary message to display
 * @param subtitle Optional secondary message
 * @param iconSize Size of the icon (default 80dp)
 * @param iconTint Color of the icon (default outline color)
 * @param titleColor Color of the title text (default onSurfaceVariant)
 * @param subtitleColor Color of the subtitle text (default outline color)
 * @param contentPadding Padding around the content (default 32dp)
 * @param modifier Modifier for the container
 */
@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    iconSize: Dp = 80.dp,
    iconTint: Color = MaterialTheme.colorScheme.outline,
    titleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    subtitleColor: Color = MaterialTheme.colorScheme.outline,
    contentPadding: Dp = 32.dp
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

            if (subtitle != null) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = subtitleColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Predefined empty states for common use cases
object EmptyStates {

    @Composable
    fun NoNotifications(
        modifier: Modifier = Modifier
    ) {
        EmptyState(
            icon = Icons.Outlined.NotificationsNone,
            title = "Không có thông báo nào",
            subtitle = "Các thông báo mới sẽ xuất hiện ở đây",
            modifier = modifier
        )
    }

    @Composable
    fun NoFollowers(
        modifier: Modifier = Modifier
    ) {
        EmptyState(
            icon = Icons.Default.PersonAdd,
            title = "Chưa có người theo dõi",
            subtitle = "Người theo dõi của bạn sẽ xuất hiện ở đây",
            iconSize = 64.dp,
            modifier = modifier
        )
    }

    @Composable
    fun NoFollowings(
        modifier: Modifier = Modifier
    ) {
        EmptyState(
            icon = Icons.Default.PersonAdd,
            title = "Chưa theo dõi ai",
            subtitle = "Hãy tìm và theo dõi những người bạn quan tâm",
            iconSize = 64.dp,
            modifier = modifier
        )
    }
}