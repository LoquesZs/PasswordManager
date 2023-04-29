package by.loqueszs.passwordmanager.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.loqueszs.passwordmanager.features.profile.domain.model.UserSettings
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userManager: UserManager,
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    val isLoggedIn = userManager.isLoggedIn
    private var userSettings: UserSettings? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userSettingsRepository.getUserSettings().collect { settings ->
                userSettings = settings
                if (!settings.authByPin) {
                    userManager.simpleAuth()
                }
            }
        }
    }

    fun logOut() {
        if (userSettings?.authByPin == true) {
            userManager.logOut()
        }
    }
}