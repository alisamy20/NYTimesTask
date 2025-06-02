package com.example.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.room.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE isBookmarked = 1")
    suspend fun getBookmarkedArticles(): List<ArticleEntity>

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT id FROM articles WHERE isBookmarked = 1")
    suspend fun getBookmarkedArticlesIds(): List<String>

    @Query("UPDATE articles SET isBookmarked = :isBookmarked WHERE id = :articleId")
    suspend fun updateBookmarkStatus(articleId: String, isBookmarked: Boolean): Int

    @Query("DELETE FROM articles")
    suspend fun clearAllBookmark()

}