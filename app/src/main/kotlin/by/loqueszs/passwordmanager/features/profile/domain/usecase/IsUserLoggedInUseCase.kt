package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val userManager: UserManager
) {
    operator fun invoke(): Boolean = userManager.isLoggedIn.value
}
