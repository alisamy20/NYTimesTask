package com.example.presentation.homelist.viewmodel

import com.example.ui.base.ViewAction
import com.example.ui.base.ViewEvent
import com.example.ui.base.ViewState
import com.example.common_kotlin.domain.model.ArticleModel


sealed class HomeAction : ViewAction {
    data object LoadArticles : HomeAction()
    data class NavigateToArticleDetails(val article: ArticleModel) : HomeAction()
    data class ToggleBookmark(val article: ArticleModel) : HomeAction()
    data object Refresh : HomeAction()
    data class SearchQueryChanged(val query: String) : HomeAction()
    data object ObserveBookmarkUpdates : HomeAction()


}

data class HomeState(
    val originalArticles: List<ArticleModel> = emptyList(),
    val filteredArticles: List<ArticleModel> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
) : ViewState


sealed class HomeViewEvent : ViewEvent {
    data class NavigateToArticleDetails(val article: ArticleModel) : HomeViewEvent()
    data object NoInternetConnection : HomeViewEvent()
}