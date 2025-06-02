package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.common_kotlin.utils.Constant.ARTICLE_DATABASE
import com.example.database.room.appdatabase.AppDataBase
import com.example.database.room.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, ARTICLE_DATABASE)
            .fallbackToDestructiveMigration(false).build()
    }


    @Singleton
    @Provides
    fun provideArticleDao(appDataBase: AppDataBase): ArticleDao = appDataBase.getArticleDao()


}