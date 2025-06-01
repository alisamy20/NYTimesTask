package com.example.common_kotlin.data.source.repo


interface BaseRepository {
    suspend fun updateBookmarkStatus(articleID: String, isBookmarked: Boolean)
}
