package by.loqueszs.passwordmanager.features.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val login: String? = null,
    val pin: String? = null
)