package com.example.core_android.base.di

import com.example.common_kotlin.base.constant.Constant.BASE_URL
import com.example.common_kotlin.base.constant.Constant.TIMEOUT
import com.example.common_kotlin.base.network.NetworkStatusChecker
import com.example.core_android.BuildConfig
import com.example.core_android.base.network.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS).build()


    @Provides
    @Singleton
    fun provideGsonConverterFactory() = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient
    ) = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(gsonConverterFactory)
        .client(okHttpClient).build()


    @Provides
    @Singleton
    fun provideNetworkStatusChecker(
        networkChecker: NetworkChecker
    ): NetworkStatusChecker = networkChecker
}
