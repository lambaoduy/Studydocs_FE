package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.data.dao.document.DocumentDao
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.mylibrary.LoadDocumentsHandler
import com.example.finalexam.handler.mylibrary.SearchDocumentsHandler
import com.example.finalexam.intent.MyLibraryIntent
import com.example.finalexam.navigation.NavigationEvent
import com.example.finalexam.reduce.MyLibraryReducer
import com.example.finalexam.result.MyLibraryResult
import com.example.finalexam.state.MyLibraryState
import com.example.finalexam.usecase.mylibrary.LoadDocumentsUseCase
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.usecase.mylibrary.SearchDocumentsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyLibraryViewModel() : ViewModel() {

    private val reducer = MyLibraryReducer()
    private val _state = MutableStateFlow(MyLibraryState())
    val state: StateFlow<MyLibraryState> = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val documentApi = RetrofitClient.createApi(DocumentApi::class.java)
    private val documentDao = DocumentDao(documentApi)

    // Khởi tạo các handler
    private val handlers: List<IntentHandler<MyLibraryIntent, MyLibraryResult>> = listOf(
        LoadDocumentsHandler(LoadDocumentsUseCase(documentDao)),
        SearchDocumentsHandler(state),

    )

    fun processIntent(intent: MyLibraryIntent) {
        // Kiểm tra đăng nhập trước khi xử lý intent
//        AuthFilter.requireLogin(app)
        viewModelScope.launch {
            val handler = handlers.find { it.canHandle(intent) }//tạo handler để xử lý
            handler?.handle(intent) {//kiểm tra intent có trong các intent xử lý được không
                    result -> _state.value = reducer.reduce(_state.value, result)//dùng reduce chuyển dữ liệu từ handler hành state và trả về
            } ?: println("[WARN] No handler for intent: $intent")
        }
    }
}
