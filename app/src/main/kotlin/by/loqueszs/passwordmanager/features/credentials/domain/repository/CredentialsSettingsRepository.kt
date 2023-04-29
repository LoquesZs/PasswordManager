package by.loqueszs.passwordmanager.features.credentials.domain.repository

import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsSettings
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import kotlinx.coroutines.flow.Flow

interface CredentialsSettingsRepository {

    suspend fun saveCredentialsOrder(order: CredentialsOrder)
    suspend fun getSettings(): Flow<CredentialsSettings>
}