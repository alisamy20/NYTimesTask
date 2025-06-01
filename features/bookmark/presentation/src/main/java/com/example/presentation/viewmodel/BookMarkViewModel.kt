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
    private val getBookmarkedPostsUseCase: GetBookmarkedArticlesUseCase,
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
                val posts = getBookmarkedPostsUseCase()
                setState { copy(bookmarkedArticles = posts, isLoading = false, error = null) }
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

    private fun toggleBookmark(post: ArticleModel) {
        val currentPosts = uiState.value.bookmarkedArticles
        val index = currentPosts.indexOfFirst { it.id == post.id }

        if (index != -1) {
            val isNowBookmarked = !post.isBookmarked

            val updatedPosts = if (isNowBookmarked) {
                currentPosts.toMutableList().apply {
                    this[index] = post.copy(isBookmarked = true)
                }
            } else {
                currentPosts.toMutableList().apply {
                    removeAt(index)
                }
            }

            setState { copy(bookmarkedArticles = updatedPosts) }

            viewModelScope.launch(ioDispatcher) {
                updateBookmarkStatusUseCase(post.id, isNowBookmarked)
                 BookmarkUpdateBus.notifyUpdate()
            }
        }
    }


}
