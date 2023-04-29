package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.model.UserSettings
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserSettingsUseCase @Inject constructor(
    private val repository: UserSettingsRepository
){

    suspend operator fun invoke(userSettings: UserSettings) {
        repository.updateUserSetting(userSettings)
    }
}
