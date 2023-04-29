package by.loqueszs.passwordmanager.features.credentials.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import by.loqueszs.passwordmanager.features.credentials.data.datasource.CredentialsSettingsDataStore
import by.loqueszs.passwordmanager.features.credentials.data.datasource.database.CredentialsDao
import by.loqueszs.passwordmanager.features.credentials.data.datasource.database.CredentialsDatabase
import by.loqueszs.passwordmanager.features.credentials.data.datasource.database.util.PassphraseManager
import by.loqueszs.passwordmanager.features.credentials.data.datasource.database.util.PassphraseManagerImpl
import by.loqueszs.passwordmanager.features.credentials.data.repository.CredentialsRepositoryImpl
import by.loqueszs.passwordmanager.features.credentials.data.repository.CredentialsSettingsRepositoryImpl
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsSettingsRepository
import by.loqueszs.passwordmanager.features.credentials.domain.usecase.AddToFavoritesUseCase
import by.loqueszs.passwordmanager.features.credentials.domain.usecase.CredentialsUseCases
import by.loqueszs.passwordmanager.features.credentials.domain.usecase.DeleteCredentialsUseCase
import by.loqueszs.passwordmanager.features.credentials.domain.usecase.GetCredentialsUseCase
import by.loqueszs.passwordmanager.features.credentials.domain.usecase.GetCredentialsWithSavedSortOrderUseCase
import by.loqueszs.passwordmanager.features.credentials.domain.usecase.RestoreCredentialsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import net.sqlcipher.database.SupportFactory

@Module
@InstallIn(SingletonComponent::class)
object CredentialsModule {

    @Provides
    @Singleton
    fun providePassphraseManager(
        @ApplicationContext context: Context
    ): PassphraseManager {
        return PassphraseManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideCredentialsDatabase(
        @ApplicationContext context: Context,
        passphraseManager: PassphraseManager
    ): CredentialsDatabase {
        return getDatabase(
            context,
            CredentialsDatabase::class.java,
            CredentialsDatabase.DATABASE_NAME
        )
            .addMigrations(CredentialsDatabase.MIGRATION_2_3)
            .openHelperFactory(SupportFactory(passphraseManager.getPassphrase()))
            .build()
    }

    private fun <T : RoomDatabase> getDatabase(context: Context, dbClass: Class<T>, dbName: String): RoomDatabase.Builder<T> {
        return Room.databaseBuilder(context, dbClass, dbName)
    }

    @Provides
    @Singleton
    fun provideCredentialsDao(db: CredentialsDatabase): CredentialsDao {
        return db.getCredentialsDao()
    }

    @Provides
    @Singleton
    fun provideCredentialsRepository(dao: CredentialsDao): CredentialsRepository {
        return CredentialsRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideCredentialsSettingsDataStore(
        @ApplicationContext context: Context
    ): CredentialsSettingsDataStore {
        return CredentialsSettingsDataStore(context)
    }

    @Provides
    @Singleton
    fun provideCredentialsSettingsRepository(
        dataStore: CredentialsSettingsDataStore
    ): CredentialsSettingsRepository {
        return CredentialsSettingsRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideListCredentialsUseCases(
        repository: CredentialsRepository,
        settingsRepository: CredentialsSettingsRepository
    ): CredentialsUseCases {
        return CredentialsUseCases(
            getCredentials = GetCredentialsUseCase(repository, settingsRepository),
            getCredentialsWithSavedSortOrderUseCase = GetCredentialsWithSavedSortOrderUseCase(repository, settingsRepository),
            deleteCredentials = DeleteCredentialsUseCase(repository),
            addToFavorites = AddToFavoritesUseCase(repository),
            restoreCredentials = RestoreCredentialsUseCase(repository)
        )
    }
}
