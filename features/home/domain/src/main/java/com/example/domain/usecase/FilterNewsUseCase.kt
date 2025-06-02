package com.example.domain.usecase

import com.example.common_kotlin.domain.model.ArticleModel
import javax.inject.Inject


class FilterNewsUseCase @Inject constructor() {
    operator fun invoke(articlesList: List<ArticleModel>, query: String) = if (query.isBlank()) {
        articlesList
    } else {
        val trimmedQuery = query.trim()
        articlesList.filter { article ->
            article.title.contains(trimmedQuery, ignoreCase = true)
        }
    }

}
