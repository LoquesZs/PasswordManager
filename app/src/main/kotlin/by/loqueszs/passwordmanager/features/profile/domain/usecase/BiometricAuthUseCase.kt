package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import javax.inject.Inject

class BiometricAuthUseCase @Inject constructor(
    private val userManager: UserManager
) {

    operator fun invoke() = userManager.simpleAuth()
}