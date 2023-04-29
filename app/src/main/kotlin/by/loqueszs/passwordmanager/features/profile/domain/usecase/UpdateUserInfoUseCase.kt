package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.model.UserInfo
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserInfoUseCase @Inject constructor(
    private val userManager: UserManager
) {

    suspend operator fun invoke(userInfo: UserInfo) {
        userManager.updateUserInfo(userInfo)
    }
}