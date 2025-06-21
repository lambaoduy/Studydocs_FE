import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.app.Application

import com.example.finalexam.intent.MyLibraryIntent
import com.example.finalexam.state.MyLibraryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.filter
import com.example.finalexam.entity.Document
import com.example.finalexam.network.AuthFilter

// ViewModel cho màn hình MyLibrary
// Nhận Intent từ UI, xử lý logic và cập nhật State
class MyLibraryViewModel(private val app: Application) : ViewModel() {
    // StateFlow để quản lý trạng thái UI
    private val _state = MutableStateFlow(MyLibraryState())
    val state: StateFlow<MyLibraryState> = _state

    // Hàm nhận Intent từ UI
    fun processIntent(intent: MyLibraryIntent) {
        // Kiểm tra đăng nhập trước khi xử lý intent
        AuthFilter.requireLogin(app)
        when (intent) {
            is MyLibraryIntent.Search -> {
                // Cập nhật từ khóa tìm kiếm và lọc danh sách tài liệu
                _state.value = _state.value.copy(searchQuery = intent.query)
                filterDocuments(intent.query)
            }
            is MyLibraryIntent.Refresh -> {
                // Tải lại danh sách tài liệu (giả lập)
                loadDocuments()
            }
            is MyLibraryIntent.UploadDocument -> {
                // Xử lý logic upload tài liệu (có thể mở màn hình upload)
                // TODO: Thực hiện logic upload
            }
            is MyLibraryIntent.SelectDocument -> {
                // Xử lý khi chọn một tài liệu (ví dụ: xem chi tiết)
                // TODO: Thực hiện logic khi chọn tài liệu
            }
        }
    }

    // Hàm lọc tài liệu theo từ khóa tìm kiếm
    private fun filterDocuments(query: String) {
        val filtered = _state.value.documents.filter {
            it.title.contains(query, ignoreCase = true)
        }
        _state.value = _state.value.copy(documents = filtered)
    }

    // Hàm tải danh sách tài liệu (giả lập, có thể thay bằng gọi backend)
    fun loadDocuments() {
        // Kiểm tra đăng nhập trước khi load tài liệu
        AuthFilter.requireLogin(app)
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            // TODO: Gọi backend hoặc repository để lấy dữ liệu thật
            // Dưới đây là dữ liệu mẫu
            val documents = listOf(
                Document(id = "1", title = "Tài liệu Kotlin", author = "Nguyễn Văn A", createdDate = "01/01/2024"),
                Document(id = "2", title = "Tài liệu Jetpack Compose", author = "Trần Thị B", createdDate = "02/01/2024"),
                Document(id = "3", title = "Tài liệu Android", author = "Lê Văn C", createdDate = "03/01/2024")
            )

            _state.value = _state.value.copy(isLoading = false, documents = documents)
        }
    }
}
