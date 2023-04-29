package by.loqueszs.passwordmanager.features.credentials.domain.usecase

class CredentialsUseCases(
    val getCredentials: GetCredentialsUseCase,
    val getCredentialsWithSavedSortOrderUseCase: GetCredentialsWithSavedSortOrderUseCase,
    val deleteCredentials: DeleteCredentialsUseCase,
    val addToFavorites: AddToFavoritesUseCase,
    val restoreCredentials: RestoreCredentialsUseCase
)