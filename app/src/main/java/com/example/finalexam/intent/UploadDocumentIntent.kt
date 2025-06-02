// Định nghĩa các Intent (hành động) cho màn hình UploadDocument
sealed class UploadDocumentIntent {
    // Hành động chọn tài liệu để upload
    data class SelectDocument(val documentId: String) : UploadDocumentIntent()
    // Hành động chọn trường đại học
    data class SelectUniversity(val universityId: String) : UploadDocumentIntent()
    // Hành động chọn khóa học trong trường
    data class SelectCourse(val courseIndex: Int) : UploadDocumentIntent()
    // Hành động nhấn nút upload
    object Upload : UploadDocumentIntent()
    // Hành động quay lại
    object Back : UploadDocumentIntent()
}

// Định nghĩa data class UploadDocument (nếu chưa có, có thể tách riêng file nếu muốn)
data class UploadDocument(
    val id: String,
    val name: String,
    var isSelected: Boolean = false
)

data class University(
    val id: String,
    val name: String,
    val courses: List<String>,
    var selectedCourseIndex: Int = 0
) 