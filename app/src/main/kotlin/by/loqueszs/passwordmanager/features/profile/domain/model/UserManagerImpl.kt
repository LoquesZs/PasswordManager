package by.loqueszs.passwordmanager.features.profile.domain.model

import android.util.Log
import by.loqueszs.passwordmanager.features.profile.data.datasource.UserInfoStorage
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Handles user information and exposes method to authenticate the user
 * and persist and retrieve user information. Handles user authentication status
 * */

@Singleton
class UserManagerImpl @Inject constructor(
    private val storage: UserInfoStorage
) : UserManager {

    companion object {
        private const val AUTH_FAILURE_RESULT = "Wrong password"
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _userInfo: MutableStateFlow<UserInfo?> = MutableStateFlow(null)

    private val _isLoggedIn = MutableStateFlow(false)
    override val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        coroutineScope.launch {
            storage.readUserInfo().collect { userInfo ->
                Log.d("UserManager", userInfo.toString())
                _userInfo.value = userInfo
            }
        }
    }

    override fun isSignedUp() = _userInfo.value != null && !_userInfo.value?.pin.isNullOrBlank() && !_userInfo.value?.login.isNullOrBlank()

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        storage.writeUserInfo(userInfo)
    }

    override suspend fun clearUserInfo() {
        storage.clearUserInfo()
    }

    override fun authWithPin(pin: String): Result<Unit> {
        return if (_userInfo.value?.pin == pin) {
            _isLoggedIn.value = true
            Result.success(Unit)
        } else {
            Result.failure(Throwable(AUTH_FAILURE_RESULT))
        }
    }

    override fun simpleAuth() {
        _isLoggedIn.value = true
    }

    override fun logOut() {
        _isLoggedIn.value = false
    }

    override fun confirmSigningUp(): Flow<Boolean> = _userInfo.map { userInfo ->
        !userInfo?.pin.isNullOrBlank() && !userInfo?.login.isNullOrBlank()
    }

    override fun getLogin(): String = _userInfo.value?.login ?: ""
}