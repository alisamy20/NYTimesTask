package com.example.data.source.remote

import com.example.data.dto.NewsResponse


interface NewsRemoteDataSource {
    suspend fun fetchArticles(): NewsResponse
}