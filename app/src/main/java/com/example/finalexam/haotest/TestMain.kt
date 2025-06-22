import com.example.finalexam.data.repository.UniversityRepository
import com.example.finalexam.usecase.university.GetAllUniversitiesUseCase
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    try {
        val repository = UniversityRepository()
        val useCase = GetAllUniversitiesUseCase(repository)

        val universities = useCase()
        println("Danh sách trường học:")
        universities.forEach { university ->
            println("- ${university.name} (ID: ${university.id})")
        }
    } catch (e: Exception) {
        println("Lỗi xảy ra khi lấy danh sách trường: ${e.message}")
    }
}
