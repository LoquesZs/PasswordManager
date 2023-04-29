package by.loqueszs.passwordmanager.features.profile.data.datasource.util

import androidx.datastore.core.Serializer
import by.loqueszs.passwordmanager.features.profile.domain.model.UserInfo
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class AccountSerializer() : Serializer<UserInfo> {

    override val defaultValue: UserInfo = UserInfo()

    override suspend fun readFrom(input: InputStream): UserInfo {
        val decryptedBytes = CryptoManager().decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = UserInfo.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserInfo, output: OutputStream) {
        CryptoManager().encrypt(
            bytes = Json.encodeToString(
                serializer = UserInfo.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}