package by.loqueszs.passwordmanager.features.credentials.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsSettings
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import by.loqueszs.passwordmanager.features.credentials.domain.util.OrderType
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val CREDENTIALS_SETTINGS_DATASTORE = "credentials_settings"

private val Context.credentialsSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = CREDENTIALS_SETTINGS_DATASTORE)

@Singleton
class CredentialsSettingsDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore: DataStore<Preferences> = context.credentialsSettingsDataStore

    private object PreferencesKeys {
        private const val NAME_SORT_ORDER = "sort_order"
        val KEY_SORT_ORDER = stringPreferencesKey(NAME_SORT_ORDER)
    }

    val credentialsSettingsFlow: Flow<CredentialsSettings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortOrder = preferences[PreferencesKeys.KEY_SORT_ORDER]?.let {
                Json.decodeFromString<CredentialsOrder>(it)
            } ?: CredentialsOrder.Date(OrderType.Ascending)
            CredentialsSettings(
                sortOrder = sortOrder
            )
        }

    suspend fun updateSortOrder(order: CredentialsOrder) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_SORT_ORDER] = Json.encodeToString(order)
        }
    }
}