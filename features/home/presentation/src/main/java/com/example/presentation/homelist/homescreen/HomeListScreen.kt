package com.example.presentation.homelist.homescreen

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.ui.base.BasicScreen
import com.example.presentation.homelist.viewmodel.HomeAction
import com.example.presentation.homelist.viewmodel.HomeState
import com.example.presentation.homelist.viewmodel.HomeViewEvent
import com.example.presentation.homelist.viewmodel.HomeViewModel
import com.example.ui.R
import com.example.ui.component.ArticleCardShimmerEffect
import com.example.ui.component.ArticleList
import com.example.ui.component.MySwipeRefresh
import com.example.ui.component.SearchBar
import com.example.ui.theme.largeTextSize
import com.example.ui.theme.padding_12
import com.example.ui.theme.padding_16
import com.example.ui.theme.padding_24


@Composable
fun HomeListScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onArticleClick: (ArticleModel) -> Unit = {},
    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiEvent by viewModel.uiEvent.collectAsState(initial = null)
    val currentOnArticleClick by rememberUpdatedState(onArticleClick)

    LaunchedEffect(Unit) {
        viewModel.handleAction(HomeAction.ObserveBookmarkUpdates)

        if (viewModel.uiState.value.originalArticles.isEmpty()) {
            viewModel.handleAction(HomeAction.LoadArticles)
        }
    }

    val noInternetMessage = stringResource(R.string.no_internet_connection)

    LaunchedEffect(uiEvent) {
        handleUiEvents(uiEvent, currentOnArticleClick, snackbarHostState, noInternetMessage)
    }

    BasicScreen(
        viewModel = viewModel,
        snackbarHostState = snackbarHostState,
        loadingContent = null,
        content = { uiState ->
            HomeContent(
                state = uiState,
                onRefresh = { viewModel.handleAction(HomeAction.Refresh) },
                onArticleClick = currentOnArticleClick,
                onQueryChanged = {
                    viewModel.handleAction(HomeAction.SearchQueryChanged(it))
                },
                onBookmarkClick = { article ->
                    viewModel.handleAction(HomeAction.ToggleBookmark(article))
                })
        })
}


private suspend fun handleUiEvents(
    uiEvent: HomeViewEvent?,
    onArticleClick: (ArticleModel) -> Unit,
    snackbarHostState: SnackbarHostState,
    noInternetMessage: String
) {
    when (uiEvent) {
        is HomeViewEvent.NavigateToArticleDetails -> {
            onArticleClick(uiEvent.article)
        }

        is HomeViewEvent.NoInternetConnection -> {
            snackbarHostState.showSnackbar(noInternetMessage)
        }

        else -> Unit
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    onRefresh: () -> Unit,
    onArticleClick: (ArticleModel) -> Unit,
    onBookmarkClick: (ArticleModel) -> Unit,
    onQueryChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        SearchBar(
            query = state.searchQuery, onQueryChanged = onQueryChanged
        )

        Spacer(modifier = Modifier.height(padding_12))

        TitlesMarquee(state.filteredArticles)

        Spacer(modifier = Modifier.height(padding_12))

        if (state.isLoading) {
            Column(
                verticalArrangement = Arrangement.spacedBy(padding_24),
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(10) {
                    ArticleCardShimmerEffect(
                        modifier = Modifier.padding(horizontal = padding_16)
                    )
                }
            }
        } else {
            SwipeRefreshList(
                isRefreshing = state.isRefreshing,
                onRefresh = onRefresh,
                articles = state.filteredArticles,
                onArticleClick = onArticleClick,
                onBookmarkClick = onBookmarkClick
            )
        }
    }
}


@Composable
private fun TitlesMarquee(articles: List<ArticleModel>) {
    val concatenatedTitles by remember(articles) {
        derivedStateOf {
            articles.joinToString(separator = " \uD83D\uDFE5 ") { it.title }
        }
    }
    Text(
        text = concatenatedTitles,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = padding_24)
            .basicMarquee(),
        fontSize = largeTextSize,
        color = colorResource(id = R.color.placeholder)
    )
}

@Composable
private fun SwipeRefreshList(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    articles: List<ArticleModel>,
    onArticleClick: (ArticleModel) -> Unit,
    onBookmarkClick: (ArticleModel) -> Unit,
    ) {
    MySwipeRefresh(
        isRefreshing = isRefreshing, onRefresh = onRefresh
    ) {
        ArticleList(articles, onArticleClick, onBookmarkClick)
    }
}



