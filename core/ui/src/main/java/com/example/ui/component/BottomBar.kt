package com.example.ui.component

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ui.navigation.AppRoute
import kotlin.collections.forEach
import kotlin.let

@Composable
fun BottomBar(navController: NavController, currentDestination: String?) {

    val items = listOf(
        AppRoute.Home, AppRoute.BookMark
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                label = { Text(stringResource(id = item.label)) },
                icon = {
                    item.icon?.let {
                        Icon(it, contentDescription = stringResource(id = item.label))
                    }
                },
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()

    BottomBar(
        navController = navController,
        currentDestination = AppRoute.Home.route
    )
}