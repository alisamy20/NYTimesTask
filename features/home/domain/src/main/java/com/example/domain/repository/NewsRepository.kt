package com.example.domain.repository

import com.example.common_kotlin.domain.model.ArticleModel


interface NewsRepository {
    suspend fun fetchNews(): List<ArticleModel>
    suspend fun insertAllNews(posts: List<ArticleModel>)
    suspend fun getAllNews() :List<ArticleModel>
    suspend fun getBookmarkedNewsIds(): List<String>
    suspend fun updateBookmarkStatus(articleID: String, isBookmarked: Boolean)
}
