package by.loqueszs.passwordmanager.features.profile.data.repository

import by.loqueszs.passwordmanager.features.profile.data.datasource.UserSettingsDataStore
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserSettingsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import by.loqueszs.passwordmanager.features.profile.domain.model.UserSettings
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class UserSettingRepositoryImpl @Inject constructor(
    private val userSettingsDataStore: UserSettingsDataStore
) : UserSettingsRepository {
    override suspend fun getUserSettings(): Flow<UserSettings> = userSettingsDataStore.userSettingsFlow

    override suspend fun updateUserSetting(settings: UserSettings) {
        userSettingsDataStore.updateUserSettings(settings)
    }


}