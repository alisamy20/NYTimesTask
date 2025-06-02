package com.example.nytimestask

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.common_kotlin.utils.Constant.ARG_ARTICLE_JSON
import com.example.common_kotlin.utils.toArticleDataModel
import com.example.common_kotlin.utils.toJson
import com.example.details.ArticleDetailsScreen
import com.example.presentation.bookmarkscreen.BookmarkScreen
import com.example.presentation.homelist.homescreen.HomeListScreen
import com.example.ui.component.BottomBar
import com.example.ui.navigation.AppRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination?.route

            MainScreen(
                navController = navController, currentDestination = currentDestination
            )
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController, currentDestination: String?
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(), bottomBar = {
            BottomBar(navController, currentDestination)
        }) { innerPadding ->
        AppGraph(navController = navController, innerPadding = innerPadding)
    }
}

@Composable
fun AppGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {

        composable(AppRoute.Home.route) {
            HomeListScreen(){
                navController.navigate(AppRoute.Details.withArgs(it.toJson()))

            }
        }

        composable(AppRoute.BookMark.route) {
            BookmarkScreen(){
                navController.navigate(AppRoute.Details.withArgs(it.toJson()))
            }
        }
        composable(
            route = "${AppRoute.Details.route}?$ARG_ARTICLE_JSON={articleJson}",
            arguments = listOf(navArgument(ARG_ARTICLE_JSON) { defaultValue = "" })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString(ARG_ARTICLE_JSON)
            val article = articleJson?.let { Uri.decode(it) }
                ?.let { runCatching { it.toArticleDataModel() }.getOrNull() }

            article?.let {
                ArticleDetailsScreen(article = it) {
                    navController.popBackStack()
                }
            }
        }
    }
}



