package by.loqueszs.passwordmanager.features.profile.di

import android.content.Context
import by.loqueszs.passwordmanager.features.profile.data.datasource.UserInfoDataStore
import by.loqueszs.passwordmanager.features.profile.data.datasource.UserInfoStorage
import by.loqueszs.passwordmanager.features.profile.data.datasource.UserSettingsDataStore
import by.loqueszs.passwordmanager.features.profile.data.repository.AuthRepositoryImpl
import by.loqueszs.passwordmanager.features.profile.data.repository.UserSettingRepositoryImpl
import by.loqueszs.passwordmanager.features.profile.domain.model.UserManagerImpl
import by.loqueszs.passwordmanager.features.profile.domain.repository.AuthRepository
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserManager
import by.loqueszs.passwordmanager.features.profile.domain.repository.UserSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideUserSettingsDataStore(
        @ApplicationContext context: Context
    ): UserSettingsDataStore {
        return UserSettingsDataStore(context)
    }

    @Provides
    @Singleton
    fun provideUserSettingsRepository(
        userSettingsDataStore: UserSettingsDataStore
    ): UserSettingsRepository {
        return UserSettingRepositoryImpl(userSettingsDataStore)
    }

    @Provides
    @Singleton
    fun provideUserInfoStorage(
        @ApplicationContext context: Context
    ): UserInfoStorage {
        return UserInfoDataStore(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        userInfoDataStore: UserInfoDataStore
    ): AuthRepository {
        return AuthRepositoryImpl(userInfoDataStore)
    }

    @Provides
    @Singleton
    fun provideUserManager(userInfoStorage: UserInfoStorage): UserManager {
        return UserManagerImpl(userInfoStorage)
    }
}