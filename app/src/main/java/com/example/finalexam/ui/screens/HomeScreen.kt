package com.example.finalexam.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.finalexam.ui.components.homeScreen.BottomBar
import com.example.finalexam.ui.components.homeScreen.Content
import com.example.finalexam.ui.components.homeScreen.TopBar
import com.example.finalexam.ui.theme.FinalExamTheme

@Composable
fun HomeScreen(
    navigateToNotification: () -> Unit,
    navigateToProfile: () -> Unit,
    onNavigateToDocumentDetail: (String) -> Unit ,
    onBottomNavItemSelected: (String) -> Unit
) {
    FinalExamTheme {
        Scaffold(
            topBar = { TopBar(navigateToNotification,navigateToProfile) },
            bottomBar = { BottomBar(onItemSelected = onBottomNavItemSelected) },
            content =
                { padding ->
                    Content(
                        modifier = Modifier.padding(padding),
                        onNavigateToDocumentDetail = onNavigateToDocumentDetail
                    )
                }
        )
    }
}