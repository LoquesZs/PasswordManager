package by.loqueszs.passwordmanager.features.profile.domain.model

data class UserSettings(
    val confirmByPin: Boolean = true,
    val authByPin: Boolean = true,
    val useBiometric: Boolean = true
)
