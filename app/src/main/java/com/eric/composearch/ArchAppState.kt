package com.eric.composearch

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eric.composearch.ui.MainDestinations
import com.eric.composearch.ui.MainScreen

/**
 * Created by eric on 2022/7/5
 */

@Composable
fun rememberArchAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
) =
    remember(scaffoldState, navController) {
        ArchAppState(scaffoldState, navController)
    }

@Stable
class ArchAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController
) {
    private val bottomBarRoutes =
        listOf(
            MainScreen.Movie.route,
            MainScreen.Discovery.route,
            MainScreen.Other.route,
            MainScreen.Mine.route
        )

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToMovieDetail(movieId: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.MOVIE_DETAIL_ROUTE}/$movieId")
        }
    }

}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED