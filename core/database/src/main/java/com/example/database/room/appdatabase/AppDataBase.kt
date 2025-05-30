package com.example.database.room.appdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.room.converters.Converters
import com.example.database.room.dao.ArticleDao
import com.example.database.room.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}