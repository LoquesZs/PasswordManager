package by.loqueszs.passwordmanager.features.profile.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthUseCases @Inject constructor(
    val authUseCase: AuthUseCase,
    val isUserSignedUp: IsUserSignedUpUseCase,
    val biometricAuthUseCase: BiometricAuthUseCase,
    val getUserSettings: IsBiometricAuthEnabledUseCase,
    val clearUserInfo: ClearUserInfoUseCase,
    val isUserLoggedIn: IsUserLoggedInUseCase
)