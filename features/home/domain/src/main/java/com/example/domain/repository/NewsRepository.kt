package com.example.domain.repository

import com.example.common_kotlin.domain.model.ArticleModel


interface NewsRepository {
    suspend fun fetchNews(): List<ArticleModel>
    suspend fun updateBookmarkStatus(articleID: String, isBookmarked: Boolean)
}
