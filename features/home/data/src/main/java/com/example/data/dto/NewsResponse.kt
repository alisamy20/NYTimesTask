package com.example.data.dto

import com.google.gson.annotations.SerializedName


data class NewsResponse(
    val status: String,
    @SerializedName("num_results") val numResults: Int,
    val results: List<ArticleDto>
)


data class ArticleDto(
    val id: String,
    val url: String,
    val title: String,
    @SerializedName("byline") val description: String,
    val type: String,
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("updated") val updatedDate: String,
    val section: String,
    val source: String,
    @SerializedName("media") val media: List<MediaDto>?
)

data class MediaDto(
    val caption: String?,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadataDto?>?,
    val subtype: String?,
    val type: String?
)

data class MediaMetadataDto(
    val format: String?, val height: Int?, val url: String?, val width: Int?
)