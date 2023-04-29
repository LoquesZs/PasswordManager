package by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.loqueszs.passwordmanager.features.credentials.domain.usecase.CredentialsUseCases
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.CredentialsEvent
import by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.CredentialsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class CredentialsListViewModel @Inject constructor(
    private val credentialsUseCases: CredentialsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(CredentialsState())
    val state: State<CredentialsState> = _state

    private var recentlyDeletedCredentialsID: Long? = null

    private var getCredentialsJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCredentialsInitially()
        }
    }

    private suspend fun getCredentialsInitially() {
        getCredentialsJob = credentialsUseCases
            .getCredentialsWithSavedSortOrderUseCase()
            .onEach { state ->
                _state.value = state
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: CredentialsEvent) {
        when (event) {
            is CredentialsEvent.DeleteCredentials -> {
                viewModelScope.launch(Dispatchers.IO) {
                    credentialsUseCases.deleteCredentials(event.id)
                }
                recentlyDeletedCredentialsID = event.id
            }

            is CredentialsEvent.RestoreCredentials -> {
                viewModelScope.launch(Dispatchers.IO) {
                    credentialsUseCases.restoreCredentials(recentlyDeletedCredentialsID ?: return@launch)
                    recentlyDeletedCredentialsID = null
                }
            }

            is CredentialsEvent.Order -> {
                if (state.value.credentialsOrder::class == event.credentialsOrder::class &&
                    state.value.credentialsOrder.orderType == event.credentialsOrder.orderType
                ) {
                    return
                }
                viewModelScope.launch(Dispatchers.IO) {
                    getCredentials(event.credentialsOrder)
                }
            }

            is CredentialsEvent.AddToFavorites -> {
                viewModelScope.launch(Dispatchers.IO) {
                    credentialsUseCases.addToFavorites(event.id)
                }
            }
        }
    }

    private suspend fun getCredentials(credentialsOrder: CredentialsOrder) {
        getCredentialsJob?.cancel()
        getCredentialsJob = credentialsUseCases.getCredentials(credentialsOrder)
            .onEach { credentials ->
                _state.value = state.value.copy(
                    credentials = credentials,
                    credentialsOrder = credentialsOrder
                )
            }.launchIn(viewModelScope)
    }
}
