package com.example.finalexam.ui.components.follow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalexam.entity.Follower
import com.example.finalexam.ui.components.common.EmptyStates

@Composable
fun FollowersContent(
    followers: List<Follower>
) {
    if (followers.isEmpty()) {
        EmptyStates.NoFollowers()
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(followers, key = { it.userId }) { follower ->
            FollowerItem(follower = follower)
        }
    }
}