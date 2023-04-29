package by.loqueszs.passwordmanager.features.profile.data.repository

import by.loqueszs.passwordmanager.features.profile.data.datasource.UserInfoDataStore
import by.loqueszs.passwordmanager.features.profile.domain.model.AuthResult
import by.loqueszs.passwordmanager.features.profile.domain.model.ErrorType
import by.loqueszs.passwordmanager.features.profile.domain.model.UserInfo
import by.loqueszs.passwordmanager.features.profile.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val userInfoDataStore: UserInfoDataStore
) : AuthRepository {

    override suspend fun getLogin(): String? {
        return runBlocking {
            userInfoDataStore.readUserInfo().first()?.login
        }
    }

    override suspend fun updateAccount(userInfo: UserInfo) {
        userInfoDataStore.writeUserInfo(userInfo)
    }

    override suspend fun auth(login: String, pin: String): Flow<AuthResult> {
        return userInfoDataStore.readUserInfo().map { account ->
            when {
                login == account?.login && pin == account?.pin -> AuthResult.Success
                login == account?.login && pin != account?.pin -> AuthResult.Error(ErrorType.WrongPassword)
                else -> AuthResult.Error(ErrorType.UserNotFound)
            }
        }
    }
}