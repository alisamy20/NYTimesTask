package com.example.nytimestask

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

        }

        composable(AppRoute.BookMark.route) {

        }
    }
}


