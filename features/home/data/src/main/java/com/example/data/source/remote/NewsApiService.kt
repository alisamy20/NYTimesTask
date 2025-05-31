package com.example.data.source.remote

import com.example.data.BuildConfig
import com.example.data.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApiService {

    @GET(BuildConfig.ENDPOINT_VIEWED_ARTICLES)
    suspend fun fetchArticles(
        @Path(BuildConfig.PATH_PERIOD) period: String = BuildConfig.DEFAULT_PERIOD,
    ): NewsResponse
}