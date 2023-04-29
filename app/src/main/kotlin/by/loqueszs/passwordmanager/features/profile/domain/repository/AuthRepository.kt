package by.loqueszs.passwordmanager.features.profile.domain.repository

import by.loqueszs.passwordmanager.features.profile.domain.model.AuthResult
import by.loqueszs.passwordmanager.features.profile.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun getLogin(): String?
    suspend fun updateAccount(userInfo: UserInfo)
    suspend fun auth(login: String, pin: String): Flow<AuthResult>
}