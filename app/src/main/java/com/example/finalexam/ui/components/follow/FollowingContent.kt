package com.example.finalexam.ui.components.follow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.entity.Following
import com.example.finalexam.ui.components.common.EmptyStates

@Composable
fun FollowingsContent(
    followings: List<Following>,
    onUnfollow: (String) -> Unit,
    onToggleNotify: (String, Boolean) -> Unit
) {
    if (followings.isEmpty()) {
        EmptyStates.NoFollowings()
        return
    }

    // Group followings by type
    val groupedFollowings = followings.groupBy { it.targetType }
    val userFollowings = groupedFollowings[FollowType.USER] ?: emptyList()
    val universityFollowings = groupedFollowings[FollowType.UNIVERSITY] ?: emptyList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Summary header
        item {
            FollowingSummaryHeader(
                userCount = userFollowings.size,
                universityCount = universityFollowings.size
            )
        }

        // Users section
        if (userFollowings.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "People",
                    count = userFollowings.size,
                    icon = Icons.Default.Person
                )
            }
            items(userFollowings, key = { it.followingId }) { following ->
                FollowingItem(
                    following = following,
                    onUnfollow = { onUnfollow(following.followingId) },
                    onToggleNotify = {
                        onToggleNotify(
                            following.followingId,
                            !following.notifyEnables
                        )
                    }
                )
            }
        }

        // Universities section
        if (universityFollowings.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Universities",
                    count = universityFollowings.size,
                    icon = Icons.Default.School
                )
            }
            items(universityFollowings, key = { it.followingId }) { following ->
                FollowingItem(
                    following = following,
                    onUnfollow = { onUnfollow(following.followingId) },
                    onToggleNotify = {
                        onToggleNotify(
                            following.followingId,
                            !following.notifyEnables
                        )
                    }
                )
            }
        }
    }

}