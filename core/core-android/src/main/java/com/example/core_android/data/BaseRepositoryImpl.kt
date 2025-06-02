package com.example.core_android.data

import com.example.common_kotlin.data.source.local.LocalDataSource
import com.example.common_kotlin.data.source.repo.BaseRepository
import javax.inject.Inject


class BaseRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : BaseRepository {


    override suspend fun updateBookmarkStatus(articleId: String, isBookmarked: Boolean) =
        localDataSource.updateBookmarkStatus(articleId, isBookmarked)


}
