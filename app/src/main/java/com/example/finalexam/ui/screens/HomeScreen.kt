package com.example.finalexam.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.finalexam.ui.components.HomeScreen.BottomBar
import com.example.finalexam.ui.components.HomeScreen.Content
import com.example.finalexam.ui.screens.HomeScreen.TopBar
import com.example.finalexam.ui.theme.FinalExamTheme

@Composable
fun HomeScreen(
    currentRoute: String = "home",
    onNavigateToDocumentDetail: (String) -> Unit = {},
    onNavigateToRoute: (String) -> Unit = {}
) {
    FinalExamTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = {
                BottomBar(
                    currentRoute = currentRoute,
                    onItemSelected = onNavigateToRoute
                )
            }
        ) { padding ->
            Content(
                modifier = Modifier.padding(padding),
                onNavigateToDocumentDetail = onNavigateToDocumentDetail
            )
        }
    }
}


