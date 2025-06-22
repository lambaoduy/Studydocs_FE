package com.example.finalexam.ui.components.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.entity.Following

@Composable
 fun FollowingItem(
    following: Following,
    onUnfollow: () -> Unit,
    onToggleNotify: () -> Unit
) {
    var showUnfollowDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar placeholder với icon phân biệt theo type
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        when (following.targetType) {
                            FollowType.USER -> MaterialTheme.colorScheme.secondaryContainer
                            FollowType.UNIVERSITY -> MaterialTheme.colorScheme.tertiaryContainer
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                when (following.targetType) {
                    FollowType.USER -> {
                        Text(
                            text = following.name.first().toString(),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    FollowType.UNIVERSITY -> {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "University",
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // User/University info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = following.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = when (following.targetType) {
                            FollowType.USER -> MaterialTheme.colorScheme.primaryContainer
                            FollowType.UNIVERSITY -> MaterialTheme.colorScheme.tertiaryContainer
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = when (following.targetType) {
                                    FollowType.USER -> Icons.Default.Person
                                    FollowType.UNIVERSITY -> Icons.Default.School
                                },
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                                tint = when (following.targetType) {
                                    FollowType.USER -> MaterialTheme.colorScheme.onPrimaryContainer
                                    FollowType.UNIVERSITY -> MaterialTheme.colorScheme.onTertiaryContainer
                                }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = when (following.targetType) {
                                    FollowType.USER -> "User"
                                    FollowType.UNIVERSITY -> "University"
                                },
                                fontSize = 10.sp,
                                color = when (following.targetType) {
                                    FollowType.USER -> MaterialTheme.colorScheme.onPrimaryContainer
                                    FollowType.UNIVERSITY -> MaterialTheme.colorScheme.onTertiaryContainer
                                }
                            )
                        }
                    }
                }
            }

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Toggle notification button
                IconButton(
                    onClick = onToggleNotify,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (following.notifyEnables)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Icon(
                        imageVector = if (following.notifyEnables)
                            Icons.Default.Notifications
                        else
                            Icons.Default.NotificationsOff,
                        contentDescription = if (following.notifyEnables)
                            "Disable notifications"
                        else
                            "Enable notifications",
                        tint = if (following.notifyEnables)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Unfollow button
                IconButton(
                    onClick = { showUnfollowDialog = true },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonRemove,
                        contentDescription = "Unfollow",
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }

    if (showUnfollowDialog) {
        AlertDialog(
            onDismissRequest = { showUnfollowDialog = false },
            title = { Text("Hủy theo dõi") },
            text = {
                Text(
                    "Bạn có chắc chắn muốn hủy theo dõi ${following.name}?" +
                            when (following.targetType) {
                                FollowType.USER -> ""
                                FollowType.UNIVERSITY -> " Bạn sẽ không còn nhận được cập nhật từ trường này."
                            }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onUnfollow()
                        showUnfollowDialog = false
                    }
                ) {
                    Text("Hủy theo dõi")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showUnfollowDialog = false }
                ) {
                    Text("Hủy bỏ")
                }
            }
        )
    }

}