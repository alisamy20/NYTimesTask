package com.example.core_android.di

import com.example.common_kotlin.utils.Constant.TIMEOUT
import com.example.common_kotlin.utils.NetworkStatusChecker
import com.example.core_android.BuildConfig
import com.example.core_android.util.network.ApiKeyInterceptor
import com.example.core_android.util.network.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor, apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(apiKeyInterceptor).addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: GsonConverterFactory, okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.NYT_BASE_URL).addConverterFactory(gson)
        .client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideNetworkStatusChecker(
        networkChecker: NetworkChecker
    ): NetworkStatusChecker = networkChecker
}
