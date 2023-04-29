package by.loqueszs.passwordmanager.features.credentials.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg credentials: CredentialsEntity)

    @Query("DELETE FROM ${CredentialsEntity.TABLE_NAME} WHERE ${CredentialsEntity.COLUMN_ID} = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM ${CredentialsEntity.TABLE_NAME} WHERE ${CredentialsEntity.COLUMN_DELETED} = 0")
    fun getAllCredentials(): Flow<List<CredentialsEntity>>

    @Query("SELECT * FROM ${CredentialsEntity.TABLE_NAME} WHERE ${CredentialsEntity.COLUMN_ID} = :id")
    fun getCredentialsByID(id: Long): Flow<CredentialsEntity>

    @Query("SELECT * FROM ${CredentialsEntity.TABLE_NAME} WHERE ${CredentialsEntity.COLUMN_DELETED} = 1")
    fun getDeletedCredentials(): Flow<List<CredentialsEntity>>

    @Query("SELECT ${CredentialsEntity.COLUMN_TITLE} FROM ${CredentialsEntity.TABLE_NAME} WHERE ${CredentialsEntity.COLUMN_DELETED} = 0")
    fun getTitleList(): List<String>

    @Update(entity = CredentialsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCredentials(credentials: CredentialsEntity)

    @Query("UPDATE ${CredentialsEntity.TABLE_NAME} SET ${CredentialsEntity.COLUMN_DELETED} = 1 WHERE ${CredentialsEntity.COLUMN_ID} = :id")
    suspend fun putInRecyclerBin(id: Long)

    @Query("UPDATE ${CredentialsEntity.TABLE_NAME} SET ${CredentialsEntity.COLUMN_FAVOURITE} = NOT ${CredentialsEntity.COLUMN_FAVOURITE} WHERE ${CredentialsEntity.COLUMN_ID} = :id")
    suspend fun addToFavorites(id: Long)

    @Query("UPDATE ${CredentialsEntity.TABLE_NAME} SET ${CredentialsEntity.COLUMN_DELETED} = 0 WHERE ${CredentialsEntity.COLUMN_ID} = :id")
    suspend fun restoreCredentials(id: Long)
}
