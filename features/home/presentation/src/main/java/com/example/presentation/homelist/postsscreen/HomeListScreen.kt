package com.example.presentation.homelist.postsscreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.presentation.homelist.viewmodel.HomeViewModel


@Composable
fun HomeListScreen(
    viewModel: HomeViewModel = hiltViewModel(), onArticleClick: (ArticleModel) -> Unit={}
) {


}
