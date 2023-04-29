package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import javax.inject.Inject

class ClearUserInfoUseCase @Inject constructor(
    private val userManager: UserManager
) {
    suspend operator fun invoke() {
        userManager.clearUserInfo()
    }
}