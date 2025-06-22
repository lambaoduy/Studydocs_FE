@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalexam.data.enums.FollowType
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.ui.components.common.ErrorState
import com.example.finalexam.ui.components.common.LoadingState
import com.example.finalexam.ui.components.follow.FollowersContent
import com.example.finalexam.ui.components.follow.FollowingsContent
import com.example.finalexam.viewmodel.FollowViewModel

@Composable
fun FollowScreen(
    viewModel: FollowViewModel = viewModel(),
    userId: String? = null,
    onBackClick: () -> Unit
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = remember { listOf("Followers", "Following") }
    val onLoad = {
        viewModel.processIntent(
            FollowIntent.GetFollowers(
                userId.toString(),
                FollowType.USER
            )
        )
        viewModel.processIntent(FollowIntent.GetFollowings)
    }
    LaunchedEffect(Unit) {
        onLoad()
    }
    val state by viewModel.state.collectAsState()


    val isLoading = state.isLoading
    val errorMessage = state.errorMessage
    val followers = state.followers
    val followings = state.followings

    val onUnfollow = { followingId: String ->
        viewModel.processIntent(FollowIntent.Unfollow(followingId))
    }

    val onToggleNotify = { followingId: String, notifyEnable: Boolean ->
        viewModel.processIntent(FollowIntent.ToggleNotifyEnable(followingId, notifyEnable))
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quản lý theo dõi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            // Content
            when {
                isLoading -> {
                    LoadingState()
                }

                errorMessage != null -> {
                    ErrorState(
                        message = errorMessage,
                        onRetry = { onLoad }
                    )
                }

                else -> {
                    when (selectedTab) {
                        0 -> FollowersContent(followers = followers)
                        1 -> FollowingsContent(
                            followings = followings,
                            onUnfollow = { followingId -> onUnfollow(followingId) },
                            onToggleNotify = { followingId, notifyEnable ->
                                onToggleNotify(followingId, notifyEnable)
                            }
                        )
                    }
                }
            }
        }
    }
}