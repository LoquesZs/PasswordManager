package by.loqueszs.passwordmanager.features.credentials.presentation.addcredentials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.loqueszs.passwordmanager.common.util.EMPTY
import by.loqueszs.passwordmanager.features.credentials.data.repository.CredentialsRepositoryImpl
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import by.loqueszs.passwordmanager.features.credentials.domain.util.getRandomString
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class AddCredentialsViewModel @Inject constructor(
    private val repository: CredentialsRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(CredentialsUIModel())
    val uiState: StateFlow<CredentialsUIModel> = _uiState.asStateFlow()

    private val titles = MutableStateFlow<List<String>>(emptyList())
    private val _titleExistsError = MutableStateFlow(false)
    val titleExistsError = _titleExistsError.asStateFlow()

    init {
        viewModelScope.launch {
//            getTitlesList().collect() { titlesList ->
//                titles.value = titlesList
//            }
        }
    }

    fun setTitle(value: String) {
        _uiState.value = _uiState.value.copy(title = value)
        _titleExistsError.value = titles.value.contains(value.trim())
    }

    fun setLogin(value: String) {
        _uiState.value = _uiState.value.copy(login = value)
    }

    fun setPassword(value: String) {
        _uiState.value = _uiState.value.copy(password = value.trim())
    }

    fun setRandomPassword() {
        _uiState.value = _uiState.value.copy(password = getRandomPassword())
    }

    fun clearPassword() {
        _uiState.value = _uiState.value.copy(password = String.EMPTY)
    }

    fun setURL(value: String) {
        _uiState.value = _uiState.value.copy(url = value)
    }

    fun setNote(value: String) {
        _uiState.value = _uiState.value.copy(note = value)
    }

    private fun getRandomPassword(): String {
        return getRandomString()
    }

    suspend fun submitCredentials() = withContext(Dispatchers.IO + SupervisorJob()) {
        repository.saveCredentials(
            _uiState.value.copy(
                title = _uiState.value.title.trim(),
                login = _uiState.value.login.trim(),
                url = _uiState.value.url.trim(),
                note = _uiState.value.note.trim(),
                dateLong = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
            )
        )
    }
}
