package com.example.database.room.localsource

import com.example.common_kotlin.data.source.local.LocalDataSource
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.database.room.dao.ArticleDao
import com.example.database.room.mapper.toDomain
import com.example.database.room.mapper.toEntity
import javax.inject.Inject





class LocalDataSourceImpl @Inject constructor(
    private val articleDao: ArticleDao
) : LocalDataSource {
    override suspend fun insertAllArticles(articles: List<ArticleModel>) {
        articleDao.insertAllArticles(articles.toEntity())
    }

    override suspend fun getBookmarkedArticles(): List<ArticleModel> {
        return articleDao.getBookmarkedArticles().toDomain()
    }

    override suspend fun getAllArticles(): List<ArticleModel> {
        return articleDao.getAllArticles().toDomain()
    }

    override suspend fun getBookmarkedArticlesIds(): List<String> {
        return articleDao.getBookmarkedArticlesIds()
    }

    override suspend fun updateBookmarkStatus(articleID: String, isBookmarked: Boolean) {
        articleDao.updateBookmarkStatus(articleID, isBookmarked)
    }

    override suspend fun clearAllBookmarks() {
        articleDao.clearAllBookmark()
    }

}
