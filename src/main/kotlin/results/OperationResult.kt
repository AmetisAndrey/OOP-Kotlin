package results

sealed class OperationResult {
    data class Success(val message: String) : OperationResult()
    data class Failure(val error: String) : OperationResult()
}