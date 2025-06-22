package com.example.finalexam.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.finalexam.entity.User
import com.example.finalexam.intent.ProfileIntent
import com.example.finalexam.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    userId: String?,
    profileViewModel: ProfileViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onImagePicker: () -> Unit = {}
) {
    val state by profileViewModel.state.collectAsState()
    var fullName by remember { mutableStateOf("") }
    var avatarUrl by remember { mutableStateOf("") }
    var initialized by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    // Lấy profile khi vào màn hình
    LaunchedEffect(userId) {
        profileViewModel.processIntent(ProfileIntent.Load)
    }

    // Khi có dữ liệu user thì gán vào form
    LaunchedEffect(state.user) {
        state.user?.let { user ->
            if (!initialized) {
                fullName = user.fullName
                avatarUrl = user.avatarUrl ?: ""
                initialized = true
            }
        }
    }

    // Hiển thị dialog thành công
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            showSuccessDialog = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Top App Bar
            TopAppBar(
                title = {
                    Text(
                        "Chỉnh sửa hồ sơ",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar Section
                AvatarSection(
                    avatarUrl = avatarUrl,
                    onImagePicker = onImagePicker,
                    onUrlChange = { avatarUrl = it }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Form Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Thông tin cá nhân",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Full Name Field
                        EnhancedInputField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = "Họ và tên",
                            icon = Icons.Default.Person,
                            placeholder = "Nhập họ và tên đầy đủ",
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        // Save Button
                        Button(
                            onClick = {
                                keyboardController?.hide()
                                val user = User(
                                    fullName = fullName,
                                    avatarUrl = avatarUrl.takeIf { it.isNotBlank() },
                                )
                                profileViewModel.processIntent(ProfileIntent.Update(user))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            enabled = !state.isLoading && fullName.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Đang lưu...")
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Lưu thay đổi",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Error Message
                        state.error?.let { error ->
                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onErrorContainer,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = error,
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Success Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                    onBackClick()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(48.dp)
                    )
                },
                title = {
                    Text(
                        text = "Thành công!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "Hồ sơ của bạn đã được cập nhật thành công.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccessDialog = false
                            onBackClick()
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
private fun AvatarSection(
    avatarUrl: String,
    onImagePicker: () -> Unit,
    onUrlChange: (String) -> Unit
) {
    var showUrlDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(120.dp)
        ) {
            if (avatarUrl.isNotBlank()) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            4.dp,
                            MaterialTheme.colorScheme.primary,
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .border(
                            4.dp,
                            MaterialTheme.colorScheme.primary,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Default Avatar",
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Edit Button
            FloatingActionButton(
                onClick = { showUrlDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(36.dp),
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Chỉnh sửa ảnh",
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Nhấn để thay đổi ảnh đại diện",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    // URL Dialog
    if (showUrlDialog) {
        var tempUrl by remember { mutableStateOf(avatarUrl) }

        AlertDialog(
            onDismissRequest = { showUrlDialog = false },
            title = { Text("Thay đổi ảnh đại diện") },
            text = {
                Column {
                    Text("Nhập URL ảnh:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempUrl,
                        onValueChange = { tempUrl = it },
                        placeholder = { Text("https://...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onUrlChange(tempUrl)
                        showUrlDialog = false
                    }
                ) {
                    Text("Lưu")
                }
            },
            dismissButton = {
                TextButton(onClick = { showUrlDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnhancedInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    placeholder: String,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
    )
}