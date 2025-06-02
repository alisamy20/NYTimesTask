package com.example.database.di


import com.example.common_kotlin.data.source.local.PreferencesManager
import com.example.database.datastore.PreferencesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesManagerModule {
    @Binds
    @Singleton
    abstract fun bindPreferencesManagerDataSource(
        preferencesManagerImpl: PreferencesManagerImpl
    ): PreferencesManager
}