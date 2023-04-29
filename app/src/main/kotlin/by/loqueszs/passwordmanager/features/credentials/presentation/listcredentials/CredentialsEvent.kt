package by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials

import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder

sealed class CredentialsEvent {
    data class Order(val credentialsOrder: CredentialsOrder) : CredentialsEvent()
    data class DeleteCredentials(val id: Long) : CredentialsEvent()
    data class AddToFavorites(val id: Long) : CredentialsEvent()
    data class RestoreCredentials(val credentialsUIModel: CredentialsUIModel) : CredentialsEvent()
}
