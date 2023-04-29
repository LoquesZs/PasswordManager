package by.loqueszs.passwordmanager.features.credentials.presentation.detailcredentials.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsRepository
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class CredentialsDetailViewModel @Inject constructor(
    private val repository: CredentialsRepository,
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    private val _item = MutableStateFlow(CredentialsUIModel(id = 0L))

    var title = mutableStateOf(_item.value.title)
        private set

    fun updateTitle(value: String) {
        title.value = value
    }

    var login = mutableStateOf(_item.value.login)
        private set

    fun updateLogin(value: String) {
        login.value = value
    }

    var password = mutableStateOf(_item.value.password)
        private set

    fun updatePassword(value: String) {
        password.value = value
    }

    var url = mutableStateOf(_item.value.url)
        private set

    fun updateUrl(value: String) {
        url.value = value
    }

    var note = mutableStateOf(_item.value.note)
        private set

    fun updateNote(value: String) {
        note.value = value
    }

    var date = mutableStateOf(_item.value.date)
        private set

    fun updateDate(value: String) {
        date.value = value
    }

    var isFavourite = mutableStateOf(_item.value.favourite)
        private set

    fun addToFavorites(value: Boolean) {
        isFavourite.value = value
    }

    private val _triggerID = MutableStateFlow<Long?>(null)

    private val _isConfirmationEnabled = MutableStateFlow(true)
    val isConfirmationEnabled = _isConfirmationEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            _triggerID.filterNotNull().collect {
                subscribeToCredentials(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            userSettingsRepository.getUserSettings().collect {
                _isConfirmationEnabled.value = it.confirmByPin
            }
        }
    }

    fun setID(value: Long) {
        _triggerID.value = value
    }

    private suspend fun subscribeToCredentials(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.getCredentialsByID(id).filterNotNull().collect {
            _item.value = it.toCredentialsUIModel()
            withContext(Dispatchers.Main) {
                title.value = _item.value.title
                login.value = _item.value.login
                password.value = _item.value.password
                date.value = _item.value.date
                url.value = _item.value.url
                note.value = _item.value.note
                isFavourite.value = _item.value.favourite
            }
        }
    }

    suspend fun saveChanges() {
        val newItem = _item.value.copy(
            title = title.value,
            login = login.value,
            password = password.value,
            url = url.value,
            note = note.value,
            favourite = isFavourite.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCredentials(newItem.toCredentialsEntity())
        }
    }

    fun setNewDate(value: LocalDateTime) {
        _item.value.copy(dateLong = value.toEpochSecond(ZoneOffset.UTC)).also {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateCredentials(it.toCredentialsEntity())
            }
        }
    }
}