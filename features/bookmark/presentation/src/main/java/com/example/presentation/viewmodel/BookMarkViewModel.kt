package com.example.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.common_kotlin.base.coroutine_dispatcher.IoDispatcher
import com.example.common_kotlin.base.event.BookmarkUpdateBus
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.common_kotlin.domain.usecase.UpdateBookmarkStatusUseCase
import com.example.domain.usecase.ClearAllBookmarksUseCase
import com.example.domain.usecase.GetBookmarkedArticlesUseCase
import com.example.ui.base.BaseMVIViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val getBookmarkedArticlesUseCase: GetBookmarkedArticlesUseCase,
    private val clearAllBookmarksUseCase: ClearAllBookmarksUseCase,
    private val updateBookmarkStatusUseCase: UpdateBookmarkStatusUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseMVIViewModel<BookmarkState, BookmarkViewEvent, BookmarkAction>(BookmarkState()) {

    override fun handleAction(action: BookmarkAction) {
        when (action) {
            is BookmarkAction.LoadBookmarks -> loadBookmarks()
            is BookmarkAction.ClearBookmarks -> clearBookmarks()
            is BookmarkAction.ToggleBookmark -> toggleBookmark(action.article)
            is BookmarkAction.NavigateToArticleDetails -> onNavigateToDetails(action)
        }
    }

    private fun onNavigateToDetails(action: BookmarkAction.NavigateToArticleDetails) {
        setEvent {
            BookmarkViewEvent.NavigateToArticleDetails(
                action.article
            )
        }
    }

    private fun loadBookmarks() {
        viewModelScope.launch(ioDispatcher) {
            setState { copy(isLoading = true) }
            try {
                val articles = getBookmarkedArticlesUseCase()
                setState { copy(bookmarkedArticles = articles, isLoading = false, error = null) }
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.message) }
                setEvent { BookmarkViewEvent.Error(e.message ?: "Unknown error") }
            }
        }
    }

    private fun clearBookmarks() {
        viewModelScope.launch(ioDispatcher) {
            try {
                clearAllBookmarksUseCase()
                setEvent { BookmarkViewEvent.BookmarksCleared }
                loadBookmarks()
            } catch (e: Exception) {
                setEvent { BookmarkViewEvent.Error(e.message ?: "Error clearing bookmarks") }
            }
        }
    }

    private fun toggleBookmark(article: ArticleModel) {
        val currentArticles = uiState.value.bookmarkedArticles
        val index = currentArticles.indexOfFirst { it.id == article.id }

        if (index != -1) {
            val isNowBookmarked = !article.isBookmarked

            val updatedArticles = if (isNowBookmarked) {
                currentArticles.toMutableList().apply {
                    this[index] = article.copy(isBookmarked = true)
                }
            } else {
                currentArticles.toMutableList().apply {
                    removeAt(index)
                }
            }

            setState { copy(bookmarkedArticles = updatedArticles) }

            viewModelScope.launch(ioDispatcher) {
                updateBookmarkStatusUseCase(article.id, isNowBookmarked)
                 BookmarkUpdateBus.notifyUpdate()
            }
        }
    }


}
