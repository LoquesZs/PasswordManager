package by.loqueszs.passwordmanager.features.profile.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import by.loqueszs.passwordmanager.features.profile.domain.model.UserSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_SETTINGS_DATASTORE = "user_settings"

private val Context.userSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS_DATASTORE)

@Singleton
class UserSettingsDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.userSettingsDataStore

    private object PreferencesKeys {
//        private const val NAME_IS_FIRST_TIME = "is_first_time"
//        val KEY_IS_FIRST_TIME = booleanPreferencesKey(NAME_IS_FIRST_TIME)

        private const val NAME_CONFIRM_BY_PIN = "confirm_by_pin"
        val KEY_CONFIRM_BY_PIN = booleanPreferencesKey(NAME_CONFIRM_BY_PIN)

        private const val NAME_AUTH_BY_PIN = "auth_by_pin"
        val KEY_AUTH_BY_PIN = booleanPreferencesKey(NAME_AUTH_BY_PIN)

        private const val NAME_USE_BIOMETRIC = "use_biometric"
        val KEY_USE_BIOMETRIC = booleanPreferencesKey(NAME_USE_BIOMETRIC)
    }

    val userSettingsFlow: Flow<UserSettings> = dataStore.data
        .map { preferences ->
            UserSettings(
//                isFirstTime = preferences[PreferencesKeys.KEY_IS_FIRST_TIME] ?: true,
                confirmByPin = preferences[PreferencesKeys.KEY_CONFIRM_BY_PIN] ?: false,
                authByPin = preferences[PreferencesKeys.KEY_AUTH_BY_PIN] ?: false,
                useBiometric = preferences[PreferencesKeys.KEY_USE_BIOMETRIC] ?: false
            )
        }

    suspend fun updateUserSettings(settings: UserSettings) {
        dataStore.edit { preferences ->
//            preferences[PreferencesKeys.KEY_IS_FIRST_TIME] = settings.isFirstTime
            preferences[PreferencesKeys.KEY_CONFIRM_BY_PIN] = settings.confirmByPin
            preferences[PreferencesKeys.KEY_AUTH_BY_PIN] = settings.authByPin
            preferences[PreferencesKeys.KEY_USE_BIOMETRIC] = settings.useBiometric
        }
    }

//    val isFirstTime: Flow<Boolean>
//        get() = dataStore.data
//            .map { preferences ->
//                preferences[PreferencesKeys.KEY_IS_FIRST_TIME] ?: true
//            }
//
//    suspend fun isNotFirstTime() {
//        dataStore.edit { settings ->
//            settings[PreferencesKeys.KEY_IS_FIRST_TIME] = false
//        }
//    }

    val confirmByPin: Flow<Boolean>
        get() = dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.KEY_CONFIRM_BY_PIN] ?: false
            }

    suspend fun setConfirmByPin(value: Boolean) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.KEY_CONFIRM_BY_PIN] = value
        }
    }

    val authByPin: Flow<Boolean>
        get() = dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.KEY_AUTH_BY_PIN] ?: false
            }

    suspend fun setAuthByPin(value: Boolean) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.KEY_AUTH_BY_PIN] = value
        }
    }

    val useBiometric: Flow<Boolean>
        get() = dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.KEY_USE_BIOMETRIC] ?: false
            }

    suspend fun setUseBiometric(value: Boolean) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.KEY_USE_BIOMETRIC] = value
        }
    }
}