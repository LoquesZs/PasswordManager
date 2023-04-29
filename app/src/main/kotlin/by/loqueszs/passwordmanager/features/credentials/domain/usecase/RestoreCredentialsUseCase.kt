package by.loqueszs.passwordmanager.features.credentials.domain.usecase

import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsRepository

class RestoreCredentialsUseCase(
    private val repository: CredentialsRepository
) {

    suspend operator fun invoke(id: Long) {
        repository.restoreCredentials(id)

    }
}