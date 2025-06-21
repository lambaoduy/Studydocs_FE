package com.example.finalexam.ui.components.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun RightFilterDrawer(
    isVisible: Boolean,
    school: String,
    onSchoolChange: (String) -> Unit,
    subject: String,
    onSubjectChange: (String) -> Unit,
    onClose: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it }),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.75f)
                .align(Alignment.TopEnd)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Bộ lọc", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.weight(0.5f))

                TextField(
                    value = school,
                    onValueChange = onSchoolChange,
                    label = { Text("Lọc theo trường") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = subject,
                    onValueChange = onSubjectChange,
                    label = { Text("Lọc theo môn học") }
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onClose,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Đóng")
                }
            }
        }
    }
}





@Preview
@Composable
fun RightFilterDrawerScreenPreview() {
    RightFilterDrawer(true, school = "",
        subject = "", onSubjectChange = {},onSchoolChange = {}, onClose = {})
}