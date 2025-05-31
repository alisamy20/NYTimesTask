package com.example.data.repository


import com.example.data.source.remote.NewsRemoteDataSource
import com.example.common_kotlin.base.network.NetworkStatusChecker
import com.example.common_kotlin.data.source.local.LocalDataSource
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.data.mapper.toDomain
import com.example.domain.repository.NewsRepository
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val networkChecker: NetworkStatusChecker
) : NewsRepository {

    override suspend fun fetchNews() = if (networkChecker.isConnected()) {
        try {
            val articlesFromRemote = getAllNewsFromRemote()
            val articlesWithBookmarks = articlesFromRemote.map { article ->
                article.copy(isBookmarked = getBookmarkedNewsIds().contains(article.id))
            }
            insertAllNews(articlesWithBookmarks)
            articlesFromRemote
        } catch (e: Exception) {
            val local = getAllNewsFromLocal()
            if (local.isNotEmpty()) local else throw e
        }
    } else {
        val local = getAllNewsFromLocal()
        if (local.isNotEmpty()) local else throw IOException("No network and no local data.")
    }


    private suspend fun insertAllNews(news: List<ArticleModel>) = localDataSource.insertAllArticles(news)


    private suspend fun getAllNewsFromLocal() = localDataSource.getAllArticles()

    private suspend fun getAllNewsFromRemote() = newsRemoteDataSource.fetchArticles().results.toDomain()


    private suspend fun getBookmarkedNewsIds() = localDataSource.getBookmarkedArticlesIds()


    override suspend fun updateBookmarkStatus(articleID: String, isBookmarked: Boolean) =
        localDataSource.updateBookmarkStatus(articleID, isBookmarked)


}

