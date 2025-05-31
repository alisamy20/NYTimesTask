package com.example.data.mapper


import com.example.common_kotlin.domain.model.*
import com.example.data.dto.*


fun List<ArticleModel>.toDto() = map { domain ->

    domain.toDto()
}

fun List<ArticleDto>.toDomain() = map { dto ->
    dto.toDomain()
}


private fun ArticleDto.toDomain() = ArticleModel(
    id = id,
    url = url,
    title = title,
    description = description,
    type = type,
    publishedDate = publishedDate,
    updatedDate = updatedDate,
    section = section,
    source = source,
    media = media?.mapNotNull { it.toDomain() },

    )

private fun ArticleModel.toDto() = ArticleDto(
    id = id,
    url = url,
    title = title,
    description = description,
    type = type,
    publishedDate = publishedDate,
    updatedDate = updatedDate,
    section = section,
    source = source,
    media = media?.map { it.toDto() })


private fun MediaDto.toDomain() = MediaModel(
    caption = caption,
    subtype = subtype,
    type = type,
    mediaMetadata = mediaMetadata?.mapNotNull { it?.toDomain() })

private fun MediaModel.toDto() = MediaDto(
    caption = caption,
    subtype = subtype,
    type = type,
    mediaMetadata = mediaMetadata?.map { it?.toDto() })

private fun MediaMetadataDto.toDomain() = MediaMetadataModel(
    format = format, height = height, url = url, width = width
)

private fun MediaMetadataModel.toDto() = MediaMetadataDto(
    format = format, height = height, url = url, width = width
)
