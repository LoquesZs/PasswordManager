package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.model.UserSettings
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class GetUserSettingsUseCase @Inject constructor(
    private val repository: UserSettingsRepository
) {

    suspend operator fun invoke(): Flow<UserSettings> = repository.getUserSettings()
}
