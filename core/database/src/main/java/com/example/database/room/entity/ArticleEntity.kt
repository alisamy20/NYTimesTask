package com.example.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.database.room.converters.Converters


@Entity(tableName = "articles")
@TypeConverters(Converters::class)
data class ArticleEntity(
    @PrimaryKey val id: String,
    val url: String,
    val title: String,
    val description: String,
    val type: String,
    val publishedDate: String,
    val updatedDate: String,
    val section: String,
    val source: String,
    val mediaJson: String?,
    val isBookmarked: Boolean = false,
    val createdUtc: Long = System.currentTimeMillis()
)





