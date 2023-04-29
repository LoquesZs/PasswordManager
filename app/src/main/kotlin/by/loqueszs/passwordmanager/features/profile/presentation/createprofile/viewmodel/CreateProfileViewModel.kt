package by.loqueszs.passwordmanager.features.profile.presentation.createprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.loqueszs.passwordmanager.features.profile.domain.model.UserInfo
import by.loqueszs.passwordmanager.features.profile.domain.usecase.ConfirmUserSignedUpUseCase
import by.loqueszs.passwordmanager.features.profile.domain.usecase.CreateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val createProfileUseCase: CreateProfileUseCase,
    confirmUserSignedUpUseCase: ConfirmUserSignedUpUseCase
) : ViewModel() {

    private val _isUserSignedUp = MutableStateFlow(false)
    val isUserSignedUp = _isUserSignedUp.asStateFlow()

    init {
        viewModelScope.launch {
            confirmUserSignedUpUseCase().collect {
                _isUserSignedUp.value = it
            }
        }
    }

    fun createAccount(login: String, pin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            createProfileUseCase(UserInfo(login, pin))
        }
    }
}