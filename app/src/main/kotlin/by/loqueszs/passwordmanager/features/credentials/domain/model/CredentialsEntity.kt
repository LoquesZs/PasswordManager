package by.loqueszs.passwordmanager.features.credentials.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.loqueszs.passwordmanager.common.util.EMPTY
import by.loqueszs.passwordmanager.features.credentials.data.datasource.database.converters.CredentialsConverters
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = CredentialsEntity.TABLE_NAME)
@TypeConverters(CredentialsConverters::class)
data class CredentialsEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = COLUMN_TITLE)
    val title: String = String.EMPTY,

    @ColumnInfo(name = COLUMN_LOGIN)
    val login: String = String.EMPTY,

    @ColumnInfo(name = COLUMN_PASSWORD)
    val password: String = String.EMPTY,

    @ColumnInfo(name = COLUMN_DATE)
    val date: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),

    @ColumnInfo(name = COLUMN_URL)
    val url: String = String.EMPTY,

    @ColumnInfo(name = COLUMN_NOTE)
    val note: String = String.EMPTY,

    @ColumnInfo(name = COLUMN_FAVOURITE, defaultValue = "0")
    val favourite: Boolean = false,

    @ColumnInfo(name = COLUMN_DELETED, defaultValue = "0")
    val deleted: Boolean = false
) {
    fun toCredentialsUIModel(): CredentialsUIModel {
        return CredentialsUIModel(
            id = id,
            title = title,
            login = login,
            password = password,
            dateLong = date,
            url = url,
            note = note,
            favourite = favourite,
            deleted = deleted
        )
    }

    companion object {
        const val TABLE_NAME = "credentials_table"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NOTE = "note"
        const val COLUMN_URL = "url"
        const val COLUMN_DATE = "date"
        const val COLUMN_FAVOURITE = "favourite"
        const val COLUMN_DELETED = "deleted"
    }
}
