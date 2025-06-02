// Định nghĩa các Intent (hành động) cho màn hình MyLibrary
sealed class MyLibraryIntent {
    // Hành động tìm kiếm tài liệu
    data class Search(val query: String) : MyLibraryIntent()
    // Hành động tải lại danh sách tài liệu
    object Refresh : MyLibraryIntent()
    // Hành động nhấn nút upload tài liệu
    object UploadDocument : MyLibraryIntent()
    // Hành động chọn một tài liệu (có thể dùng cho xem chi tiết hoặc thao tác khác)
    data class SelectDocument(val document: Document) : MyLibraryIntent()
}

// Định nghĩa data class Document (nếu chưa có, có thể tách riêng file nếu muốn)
data class Document(
    val id: String,
    val title: String,
    val author: String,
    val createdDate: String
) 