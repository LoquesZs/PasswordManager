package by.loqueszs.passwordmanager.features.profile.presentation.authorization.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.loqueszs.passwordmanager.features.profile.domain.model.AuthStatus
import by.loqueszs.passwordmanager.features.profile.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {

    private val userInputFlow = MutableStateFlow(listOf<Int>())
    private val _userInputSize = MutableStateFlow(userInputFlow.value.size)
    val userInputSize = _userInputSize.asStateFlow()

    private val _isKeyboardAvailable = MutableStateFlow(true)
    val isKeyboardAvailable = _isKeyboardAvailable.asStateFlow()

    private val _isBackspaceAvailable = MutableStateFlow(false)
    val isBackspaceAvailable = _isBackspaceAvailable.asStateFlow()

    private val _isBiometricEnabled = MutableStateFlow<Boolean?>(null)
    val isBiometricEnabled = _isBiometricEnabled.asStateFlow()

    private val _userAuthStatus: MutableStateFlow<AuthStatus> = MutableStateFlow(AuthStatus.NotAuthorized(""))
    val userAuthStatus = _userAuthStatus.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getUserSettings().collect {
                _isBiometricEnabled.value = it.useBiometric
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            userInputFlow.collect { userInput ->

                _userInputSize.value = userInput.size

                _isKeyboardAvailable.value = userInput.size in 0 until 4
                _isBackspaceAvailable.value = userInput.isNotEmpty()

                if (userInput.size == 4) {
                    _isKeyboardAvailable.value = false
                    val result = useCases.authUseCase(userInputFlow.value.toString())
                    when {
                        result.isSuccess -> {
                            _userAuthStatus.value = AuthStatus.Authorized
                        }
                        result.isFailure -> {
                            _userAuthStatus.value = AuthStatus.NotAuthorized("Wrong pin")
                        }
                    }
                }
            }
        }
    }

    fun isUserSignedUp(): Boolean = useCases.isUserSignedUp()

    fun userInputAdd(value: Int) {
        userInputFlow.value = userInputFlow.value + value
    }

    fun userInputRemoveLast() {
        userInputFlow.value = userInputFlow.value.ifEmpty { return }.dropLast(1)
    }

    fun biometricAuth() {
        useCases.biometricAuthUseCase()
    }
}