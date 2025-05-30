package com.example.common_kotlin.data.source.local

import com.example.common_kotlin.domain.model.ArticleModel


interface LocalDataSource {
    suspend fun insertAllArticles(posts: List<ArticleModel>)
    suspend fun getBookmarkedArticlesIds(): List<String>
    suspend fun updateBookmarkStatus(articleID: String, isBookmarked: Boolean)
    suspend fun getBookmarkedArticles(): List<ArticleModel>
    suspend fun getAllArticles(): List<ArticleModel>
    suspend fun clearAllBookmarks()
}
