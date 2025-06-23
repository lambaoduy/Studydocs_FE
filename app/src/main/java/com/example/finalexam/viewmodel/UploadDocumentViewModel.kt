package com.example.finalexam.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.upload.SetDocumentHandler
import com.example.finalexam.handler.upload.SetTextHandler
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.reduce.UploadDocumentReducer
import com.example.finalexam.result.UploadDocumentResult
import com.example.finalexam.state.UploadDocumentState
import com.example.finalexam.usecase.upload.UploadDocumentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel cho màn hình Upload Document theo kiến trúc MVI
 * 
 * Luồng hoạt động MVI:
 * 1. View (UploadDocumentScreen) gửi Intent thông qua processIntent()
 * 2. ViewModel tìm Handler phù hợp để xử lý Intent
 * 3. Handler thực hiện logic và tạo Result
 * 4. Reducer nhận Result và State hiện tại, tạo State mới
 * 5. View quan sát State và cập nhật UI
 * 
 * Dependencies:
 * - universityViewModel: Để lấy thông tin university và subject đã chọn
 * - uploadDocumentsUseCase: Để thực hiện logic upload
 */
class UploadDocumentViewModel(
    private val universityViewModel: UniversityViewModel,
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
) : ViewModel() {

    // Reducer để cập nhật state dựa trên result
    private val reducer = UploadDocumentReducer()
    
    // State hiện tại của màn hình upload
    private val _state = MutableStateFlow(UploadDocumentState())
    val state = _state.asStateFlow()

    //===Phần này của Hảo 22/6===
    // Context để truy cập ContentResolver
    private var context: Context? = null
    
    /**
     * Set context cho ViewModel (được gọi từ UI)
     */
    fun setContext(context: Context) {
        this.context = context
    }
    //===Phần này của Hảo 22/6===

    // Danh sách các handler để xử lý các intent khác nhau
    private val handlers: List<IntentHandler<UploadDocumentIntent, UploadDocumentResult>> = listOf(
        SetDocumentHandler(),           // Xử lý chọn/xóa document
        SetTextHandler()                // Xử lý nhập title/description
        // Xóa UploadDocumentHandler cũ khỏi đây để tránh context = null
    )

    /**
     * Xử lý intent từ View
     * 
     * @param intent Intent được gửi từ View
     */
    fun processIntent(intent: UploadDocumentIntent) {
        viewModelScope.launch {
            // Tìm handler phù hợp để xử lý intent
            val handler = handlers.find { it.canHandle(intent) }
            
            if (handler != null) {
                // Gọi handler để xử lý intent và tạo result
                handler.handle(intent) { result ->
                    _state.value = reducer.reduce(_state.value, result)
                }
            } else if (intent is UploadDocumentIntent.Upload) {
                // Xử lý upload với context và state
                val handlerWithContext = UploadDocumentHandlerWithContext(
                    uploadDocumentsUseCase = uploadDocumentsUseCase,
                    universityViewModel = universityViewModel,
                    context = context,
                    currentState = _state.value
                )
                handlerWithContext.handle(intent) { result ->
                    _state.value = reducer.reduce(_state.value, result)
                }
            } else {
                println("[WARN] No handler for intent: $intent")
            }
        }
    }
}

//===Phần này của Hảo 22/6===
/**
 * Handler wrapper để truyền context và state
 */
class UploadDocumentHandlerWithContext(
    private val uploadDocumentsUseCase: UploadDocumentsUseCase,
    private val universityViewModel: UniversityViewModel,
    private val context: Context?,
    private val currentState: UploadDocumentState
) {
    suspend fun handle(
        intent: UploadDocumentIntent,
        setResult: (UploadDocumentResult) -> Unit
    ) {
        try {
            setResult(UploadDocumentResult.Loading)
            
            // Lấy thông tin university và subject từ UniversityViewModel
            val universityState = universityViewModel.state.value
            val selectedUniversity = universityState.selectedUniversity
            
            if (selectedUniversity == null) {
                setResult(UploadDocumentResult.Error("Vui lòng chọn trường đại học"))
                return
            }
            
            val universityName = selectedUniversity.name
            val courseIndex = selectedUniversity.selectedSubjectIndex
            
            if (courseIndex < 0 || courseIndex >= selectedUniversity.subjects.size) {
                setResult(UploadDocumentResult.Error("Vui lòng chọn môn học"))
                return
            }
            
            // Lấy selected documents từ current state
            val selectedDocument = currentState.selectedDocument
            if (selectedDocument == null) {
                setResult(UploadDocumentResult.Error("Vui lòng chọn tài liệu"))
                return
            }
            
            // Cập nhật document với thông tin từ UI
            val documentWithInfo = selectedDocument.copy(
                title = currentState.title,
                description = currentState.description,
                universityName = selectedUniversity.name,
                subject = selectedUniversity.selectedSubject
            )
            
            if (context == null) {
                setResult(UploadDocumentResult.Error("Context không khả dụng"))
                return
            }
            
            val result = uploadDocumentsUseCase(
                documents = listOf(documentWithInfo),
                universityName = selectedUniversity.name,
                courseIndex = selectedUniversity.selectedSubjectIndex,
                context = context
            )
            
            setResult(result)
            
        } catch (e: Exception) {
            setResult(UploadDocumentResult.Error(e.message ?: "Upload failed"))
        }
    }
}
//===Phần này của Hảo 22/6===
