package by.loqueszs.passwordmanager.features.credentials.data.datasource.database.converters

import androidx.room.TypeConverter
import by.loqueszs.passwordmanager.utils.toFormattedString
import by.loqueszs.passwordmanager.utils.toLocalDateTime
import java.time.LocalDateTime

object CredentialsConverters {

    @JvmStatic
    @TypeConverter
    fun stringToLocalDateTime(dateTimeString: String): LocalDateTime {
        return dateTimeString.toLocalDateTime()
    }

    @JvmStatic
    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime): String {
        return localDateTime.toFormattedString()
    }
}