package com.example.presentation.viewmodel

import com.example.ui.base.ViewAction
import com.example.ui.base.ViewEvent
import com.example.ui.base.ViewState
import com.example.common_kotlin.domain.model.ArticleModel


sealed class BookmarkAction : ViewAction {
    data object LoadBookmarks : BookmarkAction()
    data object ClearBookmarks : BookmarkAction()
    data class ToggleBookmark(val article: ArticleModel) : BookmarkAction()
    data class NavigateToArticleDetails(val article: ArticleModel) : BookmarkAction()

}

data class BookmarkState(
    val bookmarkedArticles: List<ArticleModel> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: String? = null
) : ViewState


sealed class BookmarkViewEvent : ViewEvent {
    data class NavigateToArticleDetails(val article: ArticleModel) : BookmarkViewEvent()
    data object BookmarksCleared : BookmarkViewEvent()
    data class Error(val message: String) : BookmarkViewEvent()
}
