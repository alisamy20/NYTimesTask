package com.example.data.di

import com.example.data.repository.NewsRepositoryImpl
import com.example.domain.repository.NewsRepository
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRedditRepository(
        newsRepositoryImpl: NewsRepositoryImpl,
    ): NewsRepository
}
