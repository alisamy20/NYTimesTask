package com.example.core_android.base.di

import com.example.common_kotlin.base.network.NetworkStatusChecker
import com.example.core_android.base.network.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkStatusChecker(
        networkChecker: NetworkChecker
    ): NetworkStatusChecker = networkChecker
}
