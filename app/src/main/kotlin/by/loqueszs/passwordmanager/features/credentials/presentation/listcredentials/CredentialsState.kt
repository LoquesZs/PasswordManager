package by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials

import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import by.loqueszs.passwordmanager.features.credentials.domain.util.OrderType

data class CredentialsState(
    val credentials: List<CredentialsUIModel>? = null,
    val credentialsOrder: CredentialsOrder = CredentialsOrder.Date(OrderType.Ascending)
)
