package by.loqueszs.passwordmanager.features.profile.domain.repository

import by.loqueszs.passwordmanager.features.profile.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

    suspend fun getUserSettings(): Flow<UserSettings>
    suspend fun updateUserSetting(settings: UserSettings)
}
