package com.example.common_kotlin.domain.model




data class ArticleModel(
    val id: String,
    val url: String,
    val title: String,
    val description: String,
    val type: String,
    val publishedDate: String,
    val updatedDate: String,
    val section: String,
    val source: String,
    val media: List<MediaModel>?,
    val isBookmarked: Boolean = false,

    )


data class MediaModel(
    val caption: String?,
    val subtype: String?,
    val type: String?,
    val mediaMetadata: List<MediaMetadataModel?>?,

    )

data class MediaMetadataModel(
    val format: String?, val height: Int?, val url: String?, val width: Int?
)

