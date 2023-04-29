package by.loqueszs.passwordmanager.features.profile.presentation.profilescreen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.loqueszs.passwordmanager.features.profile.domain.model.UserInfo
import by.loqueszs.passwordmanager.features.profile.domain.model.UserSettings
import by.loqueszs.passwordmanager.features.profile.domain.usecase.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    var login = mutableStateOf(profileUseCases.getLoginUseCase())
        private set

    // private val _password = MutableStateFlow<String?>(null)

    var userSettings = mutableStateOf<UserSettings?>(null)
        private set

    init {
        viewModelScope.launch {
            profileUseCases.getUserSettings().collect { settings ->
                userSettings.value = settings
            }
        }
    }

    fun onLoginChange(value: String) {
        login.value = value
    }

//    fun onPasswordChange(value: String) {
//        _password.value = value
//    }

    fun setRequireConfirmation(value: Boolean) {
        userSettings.value?.copy(
            confirmByPin = value
        )?.let { updateUserSettings(it) }
    }

    fun setRequireAuth(value: Boolean) {
        userSettings.value?.copy(
            authByPin = value
        )?.let { updateUserSettings(it) }
    }

    fun setUseBiometric(value: Boolean) {
        userSettings.value?.copy(
            useBiometric = value
        )?.let { updateUserSettings(it) }
    }

    private fun updateUserSettings(value: UserSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            profileUseCases.updateUserSettings(value)
        }
    }

    fun updateUserInfo() {
        viewModelScope.launch {
            profileUseCases.updateUserInfoUseCase(UserInfo(login.value))
        }
    }
}
