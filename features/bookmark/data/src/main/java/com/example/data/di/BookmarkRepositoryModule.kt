package com.example.data.di

import com.example.data.repo.BookmarkRepositoryImpl
import com.example.domain.repository.BookmarkRepository
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookmarkRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl,
    ): BookmarkRepository
}
