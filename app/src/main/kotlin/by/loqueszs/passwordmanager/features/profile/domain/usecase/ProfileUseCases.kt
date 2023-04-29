package by.loqueszs.passwordmanager.features.profile.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileUseCases @Inject constructor(
    val isUserSignedUpUseCase: IsUserSignedUpUseCase,
    val getLoginUseCase: GetLoginUseCase,
    val updateUserInfoUseCase: UpdateUserInfoUseCase,
    val getUserSettings: GetUserSettingsUseCase,
    val updateUserSettings: UpdateUserSettingsUseCase
)