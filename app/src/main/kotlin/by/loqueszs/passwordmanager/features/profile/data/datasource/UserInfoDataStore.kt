package by.loqueszs.passwordmanager.features.profile.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import by.loqueszs.passwordmanager.features.profile.data.datasource.util.AccountSerializer
import by.loqueszs.passwordmanager.features.profile.domain.model.UserInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

private const val USER_INFO_DATASTORE_NAME = "auth_datastore"

interface UserInfoStorage {
    fun readUserInfo(): Flow<UserInfo?>
    suspend fun writeUserInfo(userInfo: UserInfo)
    suspend fun clearUserInfo()
    suspend fun updateLogin(userInfo: UserInfo)
}

private val Context.userInfoDataStore: DataStore<UserInfo> by dataStore(
    fileName = USER_INFO_DATASTORE_NAME,
    serializer = AccountSerializer()
)

@Singleton
class UserInfoDataStore @Inject constructor(
    @ApplicationContext context: Context
) : UserInfoStorage {

    private val dataStore: DataStore<UserInfo> = context.userInfoDataStore

    override suspend fun writeUserInfo(userInfo: UserInfo) {
        if (userInfo.pin == null) {
            updateLogin(userInfo)
        }
        dataStore.updateData {
            userInfo
        }
    }

    override fun readUserInfo(): Flow<UserInfo?> {
        return dataStore.data
    }

    override suspend fun clearUserInfo() {
        dataStore.updateData {
            UserInfo()
        }
    }

    override suspend fun updateLogin(userInfo: UserInfo) {
        readUserInfo().collect {
            dataStore.updateData {
                userInfo.copy(pin = it.pin)
            }
        }
    }
}
