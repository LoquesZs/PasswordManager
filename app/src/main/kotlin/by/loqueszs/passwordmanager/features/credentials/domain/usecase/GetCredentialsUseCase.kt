package by.loqueszs.passwordmanager.features.credentials.domain.usecase

import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsSettingsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import by.loqueszs.passwordmanager.features.credentials.domain.util.OrderType
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCredentialsUseCase @Inject constructor(
    private val repository: CredentialsRepository,
    private val settingsRepository: CredentialsSettingsRepository
) {

    suspend operator fun invoke(
        credentialsOrder: CredentialsOrder
    ): Flow<List<CredentialsUIModel>> {
        settingsRepository.saveCredentialsOrder(credentialsOrder)
        return repository.getAllCredentials().map { credentials ->
            when (credentialsOrder.orderType) {
                is OrderType.Ascending -> {
                    when (credentialsOrder) {
                        is CredentialsOrder.Title -> credentials.sortedBy {
                            it.title.lowercase()
                        }.map { it.toCredentialsUIModel() }
                        is CredentialsOrder.Favorites -> credentials.sortedBy {
                            it.favourite.not()
                        }.map { it.toCredentialsUIModel() }
                        is CredentialsOrder.Date -> credentials.sortedBy {
                            LocalDateTime.ofEpochSecond(it.date, 0, ZoneOffset.UTC)
                        }.map { it.toCredentialsUIModel() }
                    }
                }
                is OrderType.Descending -> {
                    when (credentialsOrder) {
                        is CredentialsOrder.Title -> credentials.sortedByDescending {
                            it.title.lowercase()
                        }.map { it.toCredentialsUIModel() }
                        is CredentialsOrder.Favorites -> credentials.sortedByDescending {
                            it.favourite.not()
                        }.map { it.toCredentialsUIModel() }
                        is CredentialsOrder.Date -> credentials.sortedByDescending {
                            LocalDateTime.ofEpochSecond(it.date, 0, ZoneOffset.UTC)
                        }.map { it.toCredentialsUIModel() }
                    }
                }
            }
        }
    }
}