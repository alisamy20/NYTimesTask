package com.example.ui.navigation

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.common_kotlin.utils.Constant.ARG_ARTICLE_JSON
import com.example.ui.R

sealed class AppRoute(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector? = null
) {
    data object Home : AppRoute("home", R.string.home, Icons.Filled.Home)
    data object BookMark : AppRoute("book mark", R.string.book_mark, Icons.Filled.Favorite)
    data object Details : AppRoute("details", R.string.details)

    fun withArgs(vararg args: String): String {
        return when (this) {
            is Details -> {
                val postJson = args.getOrNull(0).orEmpty()
                "$route?$ARG_ARTICLE_JSON=${Uri.encode(postJson)}"
            }

            else -> route
        }
    }


}
