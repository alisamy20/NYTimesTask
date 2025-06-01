package com.example.data.di

import com.example.data.source.remote.NewsRemoteDataSource
import com.example.data.source.remote.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl,
    ): NewsRemoteDataSource

}
