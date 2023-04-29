package by.loqueszs.passwordmanager.features.profile.domain.model

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val errorType: ErrorType) : AuthResult()
}

sealed class ErrorType {
    object WrongPassword : ErrorType()
    object UserNotFound : ErrorType()
}
