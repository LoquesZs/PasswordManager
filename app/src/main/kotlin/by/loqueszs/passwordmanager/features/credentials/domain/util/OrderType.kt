package by.loqueszs.passwordmanager.features.credentials.domain.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("order_type")
sealed class OrderType {

    @Serializable
    object Ascending : OrderType()

    @Serializable
    object Descending : OrderType()
}
