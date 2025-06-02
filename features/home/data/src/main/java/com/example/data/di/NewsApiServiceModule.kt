package com.example.data.di

import com.example.data.source.remote.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsApiServiceModule  {

    @Provides
    @Singleton
    fun NewsApiService(
        retrofit: Retrofit,
    ): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

}