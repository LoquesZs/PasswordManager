package by.loqueszs.passwordmanager.features.credentials.domain.repository

import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsEntity
import kotlinx.coroutines.flow.Flow

interface CredentialsRepository {

    suspend fun insertAll(vararg credentials: CredentialsEntity)

    suspend fun delete(id: Long)

    fun getAllCredentials(): Flow<List<CredentialsEntity>>

    fun getCredentialsByID(id: Long): Flow<CredentialsEntity>

    fun getDeletedCredentials(): Flow<List<CredentialsEntity>>

    suspend fun getTitleList(): List<String>

    suspend fun updateCredentials(credentials: CredentialsEntity)

    suspend fun putInRecyclerBin(id: Long)

    suspend fun restoreCredentials(id: Long)

    suspend fun addToFavorites(id: Long)
}