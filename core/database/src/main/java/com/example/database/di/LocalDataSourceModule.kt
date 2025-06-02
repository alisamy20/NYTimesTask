package com.example.database.di

import com.example.common_kotlin.data.source.local.LocalDataSource
import com.example.database.room.localsource.LocalDataSourceImpl
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        localDataSource: LocalDataSourceImpl,
    ): LocalDataSource
}
