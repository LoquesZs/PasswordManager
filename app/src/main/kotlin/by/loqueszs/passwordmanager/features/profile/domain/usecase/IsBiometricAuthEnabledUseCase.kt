package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.repository.UserSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsBiometricAuthEnabledUseCase @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) {

    suspend operator fun invoke() = userSettingsRepository.getUserSettings()
}
