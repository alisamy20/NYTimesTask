package com.example.data.repo

import com.example.common_kotlin.data.source.local.LocalDataSource
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : BookmarkRepository {

    override suspend fun getBookmarkedArticles() = localDataSource.getBookmarkedArticles()


    override suspend fun clearAllBookmarks() = localDataSource.clearAllBookmarks()


}