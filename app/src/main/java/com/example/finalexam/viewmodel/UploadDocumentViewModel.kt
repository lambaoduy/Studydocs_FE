import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.app.Application
import com.example.finalexam.entity.University
import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.network.AuthFilter
import com.example.finalexam.state.UploadDocumentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel cho màn hình UploadDocument
// Nhận Intent từ UI, xử lý logic và cập nhật State
class UploadDocumentViewModel(private val app: Application) : ViewModel() {
    // StateFlow để quản lý trạng thái UI
    private val _state = MutableStateFlow(UploadDocumentState())
    val state: StateFlow<UploadDocumentState> = _state

    // Hàm nhận Intent từ UI
    fun processIntent(intent: UploadDocumentIntent) {
        // Kiểm tra đăng nhập trước khi xử lý intent
        AuthFilter.requireLogin(app)
        when (intent) {
            is UploadDocumentIntent.SelectDocument -> {
                // Đánh dấu tài liệu được chọn hoặc bỏ chọn
                val updatedDocs = _state.value.documents.map {
                    if (it.id == intent.documentId) it.copy(isSelected = !it.isSelected) else it
                }
                _state.value = _state.value.copy(documents = updatedDocs)
            }
            is UploadDocumentIntent.SelectUniversity -> {
                // Chọn trường đại học (giả lập)
                // TODO: Lấy thông tin trường từ backend nếu cần
                val university = University(
                    id = intent.universityId,
                    name = "Đại học Nông Lâm TP.HCM",
                    courses = listOf("Công nghệ thông tin", "Khoa học dữ liệu", "Kỹ thuật phần mềm"),
                    selectedCourseIndex = 0
                )
                _state.value = _state.value.copy(university = university)
            }
            is UploadDocumentIntent.SelectCourse -> {
                // Chọn khóa học trong trường
                val updatedUni = _state.value.university?.copy(selectedCourseIndex = intent.courseIndex)
                _state.value = _state.value.copy(university = updatedUni)
            }
            is UploadDocumentIntent.Upload -> {
                // Xử lý upload tài liệu (giả lập)
                uploadDocuments()
            }
            is UploadDocumentIntent.Back -> {
                // Xử lý quay lại (có thể điều hướng về màn trước)
                // TODO: Thực hiện logic quay lại
            }
        }
    }

    // Hàm upload tài liệu (giả lập, có thể thay bằng gọi backend)
    private fun uploadDocuments() {
        viewModelScope.launch {
            // Kiểm tra đăng nhập trước khi upload
            AuthFilter.requireLogin(app)
            _state.value = _state.value.copy(isUploading = true)
            // TODO: Gọi backend hoặc repository để upload thật
            // Giả lập upload thành công
            kotlinx.coroutines.delay(1000)
            _state.value = _state.value.copy(isUploading = false, uploadSuccess = true)
        }
    }

    // Hàm khởi tạo dữ liệu mẫu (có thể gọi khi vào màn hình)
    fun loadMockData() {
        // Kiểm tra đăng nhập trước khi load dữ liệu mẫu
        AuthFilter.requireLogin(app)
        val mockDocuments = listOf(
            UploadDocument("1", "Chao_cau.docx", true),
            UploadDocument("2", "Bai_tap.pdf"),
            UploadDocument("3", "Assignment.docx")
        )
        val mockUniversity = University(
            id = "uni1",
            name = "Đại học Nông Lâm TP.HCM",
            courses = listOf("Công nghệ thông tin", "Khoa học dữ liệu", "Kỹ thuật phần mềm"),
            selectedCourseIndex = 0
        )
        _state.value = _state.value.copy(documents = mockDocuments, university = mockUniversity)
    }
}
