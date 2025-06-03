package com.example.finalexam.viewmodel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.finalexam.handler.HomeFindHandler
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.reduce.HomeReducer
import com.example.finalexam.state.HomeState
import com.example.finalexam.usecase.HomeFindUseCase
import com.example.finalexam.result.HomeResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


//file này duy viết
class HomeViewModel : ViewModel() {

    //    private val db = mutableListOf<Todo>() // DB giả lập
//     biến reducer để chuyển tất cả những gì mà usecase nhận được thành state
    private val reducer = HomeReducer()
    // Đây là biến state lưu trữ trạng thái màn hình hiện tại
//   Như là trên màn hình đang hiện sản tài liệu nào, bao nhiê tài liệu, đang ở trang tài liệu thứ mấy...
    private val _state = MutableStateFlow(HomeState())
//    val state: StateFlow<TodoState> = _state.asStateFlow()

    //     Các intent handler được khởi tạo, có các hành động gì thì thêm vào ở đây
//    như kiểu đăng ký cho view model biết là nó sẽ làm được hành động ở đây
//    biến handlers sẽ lưu trữ toàn bộ hành động mà view model nó làm được
//
    private val handlers: List<IntentHandler<HomeIntent, HomeResult>> = listOf(

        HomeFindHandler(HomeFindUseCase()),

//        AddTodoHandler(AddTodoUseCase(db)),
//        RemoveTodoHandler(RemoveTodoUseCase(db))
    )
    // xử lý intent truyền vào từ trang home ở đây
    fun processIntent(intent: HomeIntent) {
//        viewModelScope là lớp có sẵn, chịu trách nhiệm cho việc chạy gọi api, gọi db.
        //        Không phải biến tôi tạo ra nên khỏi phải đi tìm hiểu xem nó ở đâu
//        chỉ cần gọi và chạy là đc
        viewModelScope.launch {
            val handler = handlers.find { it.canHandle(intent) }
            handler?.handle(intent) { result ->
                _state.value = reducer.reduce(_state.value, result)
            } ?: println("[WARN] No handler for intent: $intent")
        }
    }
}