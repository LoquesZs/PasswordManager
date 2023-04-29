package by.loqueszs.passwordmanager.features.credentials.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.room.util.getColumnIndex
import androidx.room.util.useCursor
import androidx.sqlite.db.SupportSQLiteDatabase
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsEntity
import by.loqueszs.passwordmanager.utils.mainFormatter
import java.time.LocalDateTime
import java.time.ZoneOffset

@Database(
    entities = [CredentialsEntity::class],
    version = 3,
    exportSchema = true
)
abstract class CredentialsDatabase : RoomDatabase() {

    abstract fun getCredentialsDao(): CredentialsDao

    companion object {
        const val DATABASE_NAME = "credentials.db"
        private const val TABLE_NAME_MIGRATION = "${CredentialsEntity.TABLE_NAME}_migration"

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database) {
                    execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME_MIGRATION ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'title' TEXT NOT NULL, 'login' TEXT NOT NULL, 'password' TEXT NOT NULL, 'date' INTEGER NOT NULL, 'url' TEXT NOT NULL, 'note' TEXT NOT NULL, 'favourite' INTEGER NOT NULL DEFAULT 0, 'deleted' INTEGER NOT NULL DEFAULT 0)")
                    val columnDateCursor = database.query("SELECT * FROM ${CredentialsEntity.TABLE_NAME}")
                    columnDateCursor.useCursor { cursor ->
                        cursor.moveToFirst()
                        var id = 1
                        while (!cursor.isAfterLast) {
                            id++
                            val title = cursor.getString(getColumnIndex(cursor, CredentialsEntity.COLUMN_TITLE))
                            val login = cursor.getString(getColumnIndex(cursor, CredentialsEntity.COLUMN_LOGIN))
                            val password = cursor.getString(getColumnIndex(cursor, CredentialsEntity.COLUMN_PASSWORD))
                            val dateString = cursor.getString(getColumnIndex(cursor, CredentialsEntity.COLUMN_DATE))
                            val dateEpoch = LocalDateTime.parse(dateString, mainFormatter).toEpochSecond(ZoneOffset.UTC)
                            val url = cursor.getString(getColumnIndex(cursor, CredentialsEntity.COLUMN_URL))
                            val note = cursor.getString(getColumnIndex(cursor, CredentialsEntity.COLUMN_NOTE))
                            execSQL("INSERT INTO $TABLE_NAME_MIGRATION ('id', 'title', 'login', 'password', 'date', 'url', 'note', 'favourite', 'deleted') VALUES($id, '$title', '$login', '$password', '$dateEpoch', '$url', '$note', 0, 0)")
                            cursor.moveToNext()
                        }
                    }
                    execSQL("DROP TABLE ${CredentialsEntity.TABLE_NAME}")
                    execSQL("ALTER TABLE $TABLE_NAME_MIGRATION RENAME TO ${CredentialsEntity.TABLE_NAME}")
                }
            }
        }
    }
}
