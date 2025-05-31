package com.example.data.source.remote

import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsRemoteDataSource {

    override suspend fun fetchArticles() =
        newsApiService.fetchArticles()

}
