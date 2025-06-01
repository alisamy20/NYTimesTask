package com.example.presentation.bookmarkscreen


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.viewmodel.BookMarkViewModel
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.presentation.viewmodel.BookmarkAction
import com.example.presentation.viewmodel.BookmarkViewEvent
import com.example.ui.R
import com.example.ui.base.BasicScreen
import com.example.ui.component.ArticleList
import com.example.ui.component.CommonTopAppBar

@Composable
fun BookmarkScreen(
    viewModel: BookMarkViewModel = hiltViewModel(), onArticleClick: (ArticleModel) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val viewEvent by viewModel.uiEvent.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.handleAction(BookmarkAction.LoadBookmarks)
    }

    LaunchedEffect(viewEvent) {
        when (viewEvent) {
            is BookmarkViewEvent.NavigateToArticleDetails -> {
                onArticleClick((viewEvent as BookmarkViewEvent.NavigateToArticleDetails).article)
            }

            else -> {
            }
        }
    }

    BasicScreen(
        viewModel = viewModel,
        snackbarHostState = snackbarHostState,
        toolbar = { CommonTopAppBar(title = R.string.book_mark) },
        content = { state ->

            ArticleList(state.bookmarkedArticles, onArticleClick = {
                viewModel.handleAction(BookmarkAction.NavigateToArticleDetails(it))

            }, onBookmarkClick = {
                viewModel.handleAction(BookmarkAction.ToggleBookmark(it))

            })

        })
}

