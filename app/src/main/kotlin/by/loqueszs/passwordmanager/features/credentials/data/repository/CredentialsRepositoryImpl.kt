package by.loqueszs.passwordmanager.features.credentials.data.repository

import by.loqueszs.passwordmanager.features.credentials.data.datasource.database.CredentialsDao
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsEntity
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class CredentialsRepositoryImpl @Inject constructor(
    private val dao: CredentialsDao
) : CredentialsRepository {

    suspend fun saveCredentials(credentialsUIModel: CredentialsUIModel) = insertAll(credentialsUIModel.toCredentialsEntity())

    fun getAllTitlesList() = dao.getTitleList()

    override suspend fun insertAll(vararg credentials: CredentialsEntity) = dao.insertAll(*credentials)

    override suspend fun delete(id: Long) = dao.delete(id)

    override fun getAllCredentials(): Flow<List<CredentialsEntity>> = dao.getAllCredentials()

    override fun getCredentialsByID(id: Long): Flow<CredentialsEntity> = dao.getCredentialsByID(id)

    override fun getDeletedCredentials(): Flow<List<CredentialsEntity>> = dao.getDeletedCredentials()

    override suspend fun getTitleList(): List<String> = dao.getTitleList()

    override suspend fun updateCredentials(credentials: CredentialsEntity) = dao.updateCredentials(credentials)

    override suspend fun putInRecyclerBin(id: Long) = dao.putInRecyclerBin(id)

    override suspend fun restoreCredentials(id: Long) = dao.restoreCredentials(id)

    override suspend fun addToFavorites(id: Long) = dao.addToFavorites(id)
}
