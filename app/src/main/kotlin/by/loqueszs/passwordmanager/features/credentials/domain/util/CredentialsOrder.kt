package by.loqueszs.passwordmanager.features.credentials.domain.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("credentials_order")
sealed class CredentialsOrder {

    abstract val orderType: OrderType

    @Serializable
    class Title(override val orderType: OrderType) : CredentialsOrder()

    @Serializable
    class Date(override val orderType: OrderType) : CredentialsOrder()

    @Serializable
    class Favorites(override val orderType: OrderType) : CredentialsOrder()

    open fun copy(orderType: OrderType): CredentialsOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Favorites -> Favorites(orderType)
        }
    }
}
