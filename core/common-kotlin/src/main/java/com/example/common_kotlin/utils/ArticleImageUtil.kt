package com.example.common_kotlin.utils

import com.example.common_kotlin.utils.Constant.IMAGE_KEY
import com.example.common_kotlin.domain.model.ArticleModel


fun ArticleModel.getImageUrl(): String? {
    return media
        ?.firstOrNull { it.type == IMAGE_KEY }
        ?.mediaMetadata
        ?.filterNotNull()
        ?.maxByOrNull { (it.width ?: 0) * (it.height ?: 0) }
        ?.url
}