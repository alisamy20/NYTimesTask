package com.example.core_android.base.di

import com.example.common_kotlin.data.source.repo.BaseRepository
import com.example.core_android.base.data.BaseRepositoryImpl
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBaseRepository(
        baseRepositoryImpl: BaseRepositoryImpl,
    ): BaseRepository
}
