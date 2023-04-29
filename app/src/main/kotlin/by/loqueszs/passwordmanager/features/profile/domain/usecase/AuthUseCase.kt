package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthUseCase @Inject constructor(
    private val userManager: UserManager
) {
    operator fun invoke(pin: String): Result<Unit> = userManager.authWithPin(pin)
}