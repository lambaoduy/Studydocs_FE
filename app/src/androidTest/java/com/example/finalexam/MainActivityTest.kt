package com.example.finalexam

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalexam.ui.theme.FinalExamTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun uploadButton_navigatesToUploadScreen() {
        // Kiểm tra nút "Tải tài liệu lên" hiển thị
        composeTestRule
            .onNodeWithTag("upload_button")
            .assertIsDisplayed()
            .performClick()

        // Kiểm tra màn hình Upload đã xuất hiện (ví dụ kiểm tra text hoặc tag)
        composeTestRule
            .onNodeWithText("Tải tài liệu lên") // text trong UploadDocumentScreen
            .assertIsDisplayed()
    }
}
