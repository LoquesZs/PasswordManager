package by.loqueszs.passwordmanager.features.profile.domain.usecase

import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ConfirmUserSignedUpUseCase @Inject constructor(
    private val userManager: UserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return userManager.confirmSigningUp()
    }
}