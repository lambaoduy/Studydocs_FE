package com.example.finalexam.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.data.dao.document.DocumentDao
import com.example.finalexam.handler.HomeScreen.HomeFindWithFiltersHandler
import com.example.finalexam.handler.HomeScreen.HomeGetAllHandler
import com.example.finalexam.handler.HomeScreen.HomeLoadByUerIDHandler
import com.example.finalexam.handler.HomeScreen.NavigateToDocumentDetailHandler
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.reduce.HomeReducer
import com.example.finalexam.result.HomeResult
import com.example.finalexam.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


//file này duy viết
class HomeViewModel(app: Application) : ViewModel() {

    //     biến reducer để chuyển tất cả những gì mà usecase nhận được thành state
    private val reducer = HomeReducer()

    // Đây là biến state lưu trữ trạng thái màn hình hiện tại
//   Như là trên màn hình đang hiện sản tài liệu nào, bao nhiê tài liệu, đang ở trang tài liệu thứ mấy...
    private val _state = MutableStateFlow(HomeState(listOf()))
    val state: StateFlow<HomeState> = _state.asStateFlow()
    val documentApi = RetrofitClient.createApi(DocumentApi::class.java)
    private val documentDao = DocumentDao(documentApi)
    //     Các intent handler được khởi tạo, có các hành động gì thì thêm vào ở đây
//    như kiểu đăng ký cho view model biết là nó sẽ làm được hành động ở đây
//    biến handlers sẽ lưu trữ toàn bộ hành động mà view model nó làm được
//

    private val handlers: List<IntentHandler<HomeIntent, HomeResult>> = listOf(
        HomeLoadByUerIDHandler(documentDao),//lấy dữ liệu theo id user
        HomeGetAllHandler(documentDao),// lấy tất cả dữ liệu
        NavigateToDocumentDetailHandler(),
        HomeFindWithFiltersHandler(documentDao, state.value.listDocument)
    )

    // xử lý intent truyền vào từ trang home ở đây
    fun processIntent(intent: HomeIntent) {
        viewModelScope.launch {
            val handler = handlers.find { it.canHandle(intent) }//tạo handler để xử lý
            handler?.handle(intent) {//kiểm tra intent có trong các intent xử lý được không
                    result ->
                _state.value = reducer.reduce(
                    _state.value,
                    result
                )//dùng reduce chuyển dữ liệu từ handler hành state và trả về
            } ?: println("[WARN] No handler for intent: $intent")
        }
    }
}