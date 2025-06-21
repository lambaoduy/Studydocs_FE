package com.example.finalexam.navigation

// Định nghĩa các sự kiện navigation có thể xảy ra trong ứng dụng
sealed class NavigationEvent {
    // Sự kiện chuyển đến màn hình upload tài liệu
    data object NavigateToUpload : NavigationEvent()
    
    // Sự kiện quay lại màn hình trước đó
    data object NavigateBack : NavigationEvent()
    
    // Sự kiện quay về trang chủ
    data object NavigateToHome : NavigationEvent()
    
    // Sự kiện chuyển đến màn hình chi tiết tài liệu
    data class NavigateToDocumentDetail(val documentId: String) : NavigationEvent()
}