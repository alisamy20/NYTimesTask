package com.example.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.ui.theme.padding_16
import com.example.ui.theme.padding_8


@Composable
fun ArticleList(
    articles: List<ArticleModel>,
    onArticleClick: (ArticleModel) -> Unit,
    onBookmarkClick: (ArticleModel) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(padding_16),
        verticalArrangement = Arrangement.spacedBy(padding_8)
    ) {
        items(articles.size) { index ->
            val article = articles[index]
            ArticleItem(
                article = article,
                onArticleClick=onArticleClick,
                onBookmarkClick = onBookmarkClick
            )
        }
    }
}