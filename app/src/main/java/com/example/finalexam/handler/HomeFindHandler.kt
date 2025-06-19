//package com.example.finalexam.handler
//
//import com.example.finalexam.intent.HomeIntent
//import com.example.finalexam.result.HomeResult
//import com.example.finalexam.usecase.HomeFindUseCase
//
//// thiện làm: Handler cho HomeIntent.FindTodo
//class HomeFindHandler(private val useCase: HomeFindUseCase) : IntentHandler<HomeIntent, HomeResult> {
//    override fun canHandle(intent: HomeIntent): Boolean = intent is HomeIntent.FindTodo
//
//    override suspend fun handle(intent: HomeIntent, setResult: (HomeResult) -> Unit) {
//        if (intent is HomeIntent.FindTodo) {
//            setResult(HomeResult.Loading)
//            try {
//                val result = useCase.invoke() // hoặc truyền search nếu cần
//                setResult(HomeResult.Find(result))
//            } catch (e: Exception) {
//                setResult(HomeResult.Error(e))
//            }
//        }
//    }
//}