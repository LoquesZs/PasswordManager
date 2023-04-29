package by.loqueszs.passwordmanager.features.profile.domain.model

sealed class AuthStatus {
    object Authorized : AuthStatus()
    data class NotAuthorized(val message: String) : AuthStatus()
}
