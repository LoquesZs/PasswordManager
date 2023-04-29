package by.loqueszs.passwordmanager.features.credentials.domain.usecase

import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsEntity
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsSettings
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsSettingsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import by.loqueszs.passwordmanager.features.credentials.domain.util.OrderType
import by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.CredentialsState
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCredentialsWithSavedSortOrderUseCase(
    private val repository: CredentialsRepository,
    private val settingsRepository: CredentialsSettingsRepository
) {
    suspend operator fun invoke(): Flow<CredentialsState> {
        return combine(
            repository.getAllCredentials(),
            settingsRepository.getSettings()
        ) { credentials: List<CredentialsEntity>, settings: CredentialsSettings ->

            val sortOrder = settings.sortOrder

            val credentialsList = when (sortOrder.orderType) {
                is OrderType.Ascending -> {
                    when (settings.sortOrder) {
                        is CredentialsOrder.Title -> credentials.sortedBy {
                            it.title.lowercase()
                        }
                        is CredentialsOrder.Favorites -> credentials.sortedBy {
                            it.favourite.not()
                        }
                        is CredentialsOrder.Date -> credentials.sortedBy {
                            LocalDateTime.ofEpochSecond(it.date, 0, ZoneOffset.UTC)
                        }
                    }
                }
                is OrderType.Descending -> {
                    when (settings.sortOrder) {
                        is CredentialsOrder.Title -> credentials.sortedByDescending {
                            it.title.lowercase()
                        }
                        is CredentialsOrder.Favorites -> credentials.sortedByDescending {
                            it.favourite.not()
                        }
                        is CredentialsOrder.Date -> credentials.sortedByDescending {
                            LocalDateTime.ofEpochSecond(it.date, 0, ZoneOffset.UTC)
                        }
                    }
                }
            }.map { entity ->
                entity.toCredentialsUIModel()
            }

            CredentialsState(
                credentials = credentialsList,
                credentialsOrder = sortOrder
            )
        }
    }
}