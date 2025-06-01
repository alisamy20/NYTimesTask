package com.example.domain.repository

import com.example.common_kotlin.domain.model.ArticleModel


interface BookmarkRepository {
    suspend fun getBookmarkedArticles(): List<ArticleModel>
    suspend fun clearAllBookmarks()
}
