package by.loqueszs.passwordmanager.features.credentials.data.repository

import by.loqueszs.passwordmanager.features.credentials.data.datasource.CredentialsSettingsDataStore
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsSettings
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsSettingsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class CredentialsSettingsRepositoryImpl @Inject constructor(
    private val credentialsSettingsDataStore: CredentialsSettingsDataStore
) : CredentialsSettingsRepository {
    override suspend fun saveCredentialsOrder(order: CredentialsOrder) {
        credentialsSettingsDataStore.updateSortOrder(order)
    }

    override suspend fun getSettings(): Flow<CredentialsSettings> = credentialsSettingsDataStore.credentialsSettingsFlow
}