package by.loqueszs.passwordmanager.features.credentials.domain.usecase

import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsRepository

class AddToFavoritesUseCase(
    private val repository: CredentialsRepository
) {

    suspend operator fun invoke(id: Long) {
        repository.addToFavorites(id)
    }
}