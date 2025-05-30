package com.example.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ui.R

sealed class AppRoute(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector? = null
) {
    data object Home : AppRoute("home", R.string.home, Icons.Filled.Home)
    data object Favorites : AppRoute("book mark", R.string.book_mark, Icons.Filled.Favorite)
    data object Details : AppRoute("details", R.string.details)

}
