package com.example.database.room.converters

import androidx.room.TypeConverter
import com.example.common_kotlin.domain.model.MediaModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromMediaList(media: List<MediaModel>?): String? {
        return if (media == null) null else gson.toJson(media)
    }

    @TypeConverter
    fun toMediaList(mediaJson: String?): List<MediaModel>? {
        if (mediaJson.isNullOrEmpty()) return null

        val type = object : TypeToken<List<MediaModel>>() {}.type
        return gson.fromJson(mediaJson, type)
    }
}