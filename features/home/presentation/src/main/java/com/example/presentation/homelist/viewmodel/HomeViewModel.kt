package com.example.presentation.homelist.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.lifecycle.viewModelScope
import com.example.common_kotlin.base.coroutine_dispatcher.IoDispatcher
import com.example.common_kotlin.base.event.BookmarkUpdateBus
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.domain.usecase.GetNewsUseCase
import com.example.ui.base.BaseMVIViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import com.example.common_kotlin.base.network.Resource
import com.example.common_kotlin.domain.usecase.UpdateBookmarkStatusUseCase
import com.example.domain.usecase.FilterNewsUseCase


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val updateBookmarkStatusUseCase: UpdateBookmarkStatusUseCase,
    private val filterPostsUseCase: FilterNewsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseMVIViewModel<HomeState, HomeViewEvent, HomeAction>(HomeState()) {


    override fun handleAction(action: HomeAction) {
        when (action) {
            is HomeAction.LoadArticles -> getNewArticle()
            is HomeAction.Refresh -> getNewArticle(isRefresh = true)
            is HomeAction.ToggleBookmark -> toggleBookmark(action.article)
            is HomeAction.NavigateToArticleDetails -> navigateToArticleDetails(action.article)
            is HomeAction.SearchQueryChanged -> updateSearchQuery(action.query)
            is HomeAction.ObserveBookmarkUpdates -> observeBookmarkUpdates()

        }
    }


    private fun observeBookmarkUpdates() {
        viewModelScope.launch {
            BookmarkUpdateBus.events.collect {
                handleAction(HomeAction.LoadArticles)
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getNewArticle(isRefresh: Boolean = false) {
        getNewsUseCase(Unit).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    setState { copy(isLoading = true, isRefreshing = isRefresh) }
                }

                is Resource.Success -> {
                    setState {
                        copy(
                            originalArticles = resource.value,
                            filteredArticles = resource.value,
                            isLoading = false,
                            isRefreshing = false,
                            error = null
                        )
                    }
                }

                is Resource.Failure -> {
                    setState {
                        copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = resource.exception.message
                        )
                    }
                }

                Resource.Empty -> {
                    setState {
                        copy(
                            originalArticles = emptyList(),
                            filteredArticles = emptyList(),
                            isLoading = false,
                            isRefreshing = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun toggleBookmark(article: ArticleModel) {
        val updatedArticle = article.copy(isBookmarked = !article.isBookmarked)
        val updatedArticles = uiState.value.originalArticles.map {
            if (it.id == article.id) updatedArticle else it
        }

        setState {
            copy(originalArticles = updatedArticles, filteredArticles = updatedArticles.filter {
                it.title.contains(
                    uiState.value.searchQuery, ignoreCase = true
                )
            })
        }

        viewModelScope.launch(ioDispatcher) {
            updateBookmarkStatusUseCase(article.id, updatedArticle.isBookmarked)
        }
    }

    private fun navigateToArticleDetails(article: ArticleModel) {
        setEvent { HomeViewEvent.NavigateToArticleDetails(article) }
    }

    private fun updateSearchQuery(query: String) {
        setState { copy(searchQuery = query) }
        filterArticles(query)
    }

    private fun filterArticles(query: String) {
        setState {
            copy(
                filteredArticles = filterPostsUseCase(
                    uiState.value.originalArticles, query
                )
            )
        }
    }


}
