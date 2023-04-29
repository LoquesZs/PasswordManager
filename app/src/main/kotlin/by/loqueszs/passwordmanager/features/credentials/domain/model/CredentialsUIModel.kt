package by.loqueszs.passwordmanager.features.credentials.domain.model

import by.loqueszs.passwordmanager.common.util.EMPTY
import by.loqueszs.passwordmanager.utils.toFormattedString
import java.time.LocalDateTime
import java.time.ZoneOffset

data class CredentialsUIModel(
    val id: Long = 0L,
    val title: String = String.EMPTY,
    val login: String = String.EMPTY,
    val password: String = String.EMPTY,
    private val dateLong: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
    val url: String = String.EMPTY,
    val note: String = String.EMPTY,
    val favourite: Boolean = false,
    val deleted: Boolean = false
) {
    companion object {
        private const val URL_PREFIX = "https://"
    }

    val date = LocalDateTime.ofEpochSecond(dateLong, 0, ZoneOffset.UTC).toFormattedString()

    fun toCredentialsEntity(): CredentialsEntity {
        return CredentialsEntity(
            id = id,
            title = title,
            login = login,
            password = password,
            url = url,
            date = dateLong,
            note = note,
            favourite = favourite,
            deleted = deleted
        )
    }
}

class InvalidCredentialsException(message: String) : Exception(message)
