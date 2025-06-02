package com.example.database.room.mapper

import com.example.common_kotlin.domain.model.ArticleModel
import com.example.database.room.converters.Converters
import com.example.database.room.entity.ArticleEntity


fun List<ArticleModel>.toEntity() = map { domain ->

    domain.toEntity()
}

fun List<ArticleEntity>.toDomain() = map { entity ->
    entity.toDomain()
}


private fun ArticleModel.toEntity(): ArticleEntity = ArticleEntity(
    id = id,
    url = url,
    title = title,
    description = description,
    type = type,
    publishedDate = publishedDate,
    updatedDate = updatedDate,
    section = section,
    source = source,
    mediaJson = Converters().fromMediaList(media),
    isBookmarked = isBookmarked
)

private fun ArticleEntity.toDomain(): ArticleModel = ArticleModel(
    id = id,
    url = url,
    title = title,
    description = description,
    type = type,
    publishedDate = publishedDate,
    updatedDate = updatedDate,
    section = section,
    source = source,
    media = Converters().toMediaList(mediaJson),
    isBookmarked = isBookmarked
)





