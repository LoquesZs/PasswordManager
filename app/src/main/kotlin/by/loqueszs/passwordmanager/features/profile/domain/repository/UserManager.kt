package by.loqueszs.passwordmanager.features.profile.domain.repository

import by.loqueszs.passwordmanager.features.profile.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserManager {

    val isLoggedIn: StateFlow<Boolean>

    fun isSignedUp(): Boolean
    fun confirmSigningUp(): Flow<Boolean>

    fun getLogin(): String
    suspend fun updateUserInfo(userInfo: UserInfo)
    suspend fun clearUserInfo()

    fun authWithPin(pin: String): Result<Unit>
    fun simpleAuth()

    fun logOut()
}