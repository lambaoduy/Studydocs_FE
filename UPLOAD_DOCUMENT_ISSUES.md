# Upload Document - Issues và Fixes

## 🔍 Vấn đề đã phát hiện

### 1. **UniversityViewModelFactory thiếu tham số**
```kotlin
// ❌ HIỆN TẠI (SAI)
val universityViewModel: UniversityViewModel = viewModel(
    factory = remember {
        UniversityViewModelFactory() // Thiếu tham số!
    }
)

// ✅ CẦN SỬA THÀNH
val getAllUniversitiesUseCase = remember { GetAllUniversitiesUseCase() }
val addSubjectUseCase = remember { AddSubjectUseCase() }

val universityViewModel: UniversityViewModel = viewModel(
    factory = remember {
        UniversityViewModelFactory(getAllUniversitiesUseCase, addSubjectUseCase)
    }
)
```

### 2. **UploadDocumentViewModel constructor không khớp với Factory**
```kotlin
// ❌ Factory đang truyền UniversityViewModel
class UploadDocumentViewModelFactory(
    private val universityViewModel: UniversityViewModel
) {
    return UploadDocumentViewModel(universityViewModel) as T
}

// ✅ Nhưng ViewModel constructor chỉ nhận UploadDocumentsUseCase
class UploadDocumentViewModel(
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
)
```

### 3. **UploadDocumentHandler chưa sử dụng university data**
```kotlin
// ❌ HIỆN TẠI
val selectedDocuments = emptyList<UploadDocument>() // Hardcoded empty list
val universityId = "" // Hardcoded empty string
val courseIndex = 0 // Hardcoded 0

// ✅ CẦN SỬA
val universityState = universityViewModel.state.value
val selectedUniversity = universityState.selectedUniversity
val universityId = selectedUniversity?.id ?: ""
val courseIndex = selectedUniversity?.selectedSubjectIndex ?: 0
```

## 🔧 Các bước sửa cần thực hiện

### Bước 1: Thêm imports cần thiết
```kotlin
import com.example.finalexam.usecase.university.AddSubjectUseCase
import com.example.finalexam.usecase.university.GetAllUniversitiesUseCase
import com.example.finalexam.usecase.upload.UploadDocumentsUseCase
```

### Bước 2: Sửa UploadDocumentScreen.kt
```kotlin
@Composable
fun UploadDocumentScreen(...) {
    // Tạo UseCase dependencies
    val getAllUniversitiesUseCase = remember { GetAllUniversitiesUseCase() }
    val addSubjectUseCase = remember { AddSubjectUseCase() }
    val uploadDocumentsUseCase = remember { UploadDocumentsUseCase() }
    
    // Khởi tạo ViewModels với đúng dependencies
    val universityViewModel: UniversityViewModel = viewModel(
        factory = remember {
            UniversityViewModelFactory(getAllUniversitiesUseCase, addSubjectUseCase)
        }
    )
    
    val uploadDocumentViewModel: UploadDocumentViewModel = viewModel(
        factory = remember {
            UploadDocumentViewModelFactory(universityViewModel, uploadDocumentsUseCase)
        }
    )
}
```

### Bước 3: Cập nhật UploadDocumentViewModelFactory
```kotlin
class UploadDocumentViewModelFactory(
    private val universityViewModel: UniversityViewModel,
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadDocumentViewModel::class.java)) {
            return UploadDocumentViewModel(universityViewModel, uploadDocumentsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

### Bước 4: Cập nhật UploadDocumentViewModel constructor
```kotlin
class UploadDocumentViewModel(
    private val universityViewModel: UniversityViewModel,
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
) : ViewModel() {
    // ... rest of the code
}
```

## 📋 Luồng hoạt động sau khi sửa

### 1. **Khởi tạo Dependencies**
```
UploadDocumentScreen
├── GetAllUniversitiesUseCase
├── AddSubjectUseCase  
├── UploadDocumentsUseCase
├── UniversityViewModel (với UseCases)
└── UploadDocumentViewModel (với UniversityViewModel + UploadUseCase)
```

### 2. **Luồng Upload Document**
```
User clicks Upload
├── UploadDocumentScreen.processIntent(UploadIntent)
├── UploadDocumentViewModel.processIntent()
├── UploadDocumentHandler.handle()
│   ├── Lấy university data từ UniversityViewModel
│   ├── Validate university và subject
│   └── Gọi UploadDocumentsUseCase
├── UploadDocumentsUseCase.invoke()
│   ├── Validate input
│   ├── Upload files (TODO)
│   └── Call API (TODO)
├── UploadDocumentResult
├── UploadDocumentReducer.reduce()
└── UI updates
```

### 3. **State Management**
```
UploadDocumentState
├── title: String
├── description: String  
├── selectedDocument: UploadDocument?
├── isUploading: Boolean
├── uploadSuccess: Boolean
├── uploadedDocuments: List<UploadDocument>
└── error: String?

UniversityState
├── universityList: List<University>
├── selectedUniversity: University?
└── error: String?
```

## 🚀 TODO sau khi sửa xong

1. **Implement real API calls** trong UploadDocumentsUseCase
2. **Add file upload service** (Firebase Storage, AWS S3)
3. **Add progress tracking** cho upload
4. **Add retry mechanism** cho failed uploads
5. **Add form validation** chi tiết hơn
6. **Add unit tests** cho các UseCase và Handler
7. **Add error handling** tốt hơn với user-friendly messages

## 📝 Notes

- Tất cả các file đã được comment chi tiết để dễ đọc và maintain
- Architecture MVI đã được implement đúng pattern
- Dependency injection đã được thiết lập
- Chỉ cần sửa các vấn đề dependency injection và implement real API calls 