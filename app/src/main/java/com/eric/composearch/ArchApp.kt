package com.eric.composearch

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.eric.composearch.ui.*
import com.eric.composearch.ui.theme.ComposeArchTheme

/**
 * Created by eric on 2022/7/5
 */
private val mainScreenItems =
    listOf(MainScreen.Movie, MainScreen.Discovery, MainScreen.Other, MainScreen.Mine)

@Composable
fun ArchApp(onFinish: () -> Unit) {
    ComposeArchTheme {
        val appState = rememberArchAppState()
        Scaffold(bottomBar = {
            if (appState.shouldShowBottomBar) {
                AppBottomNavigation(appState.currentRoute, appState::navigateToBottomBarRoute)
            }
        }
        ) { innerPadding ->
            AppNavHost(appState, Modifier.padding(innerPadding), onFinish)
        }
    }
}

@Composable
private fun AppBottomNavigation(currentRoute: String?, navigateToRoute: (String) -> Unit) {
    BottomNavigation {
        mainScreenItems.forEach { screen ->
            val selected = screen.route == currentRoute
            BottomNavigationItem(
                icon = {
                    Icon(
                        if (selected) screen.selectedIcon else screen.normalIcon,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(screen.title)) },
                selected = selected,
                onClick = {
                    navigateToRoute(screen.route)
                }
            )
        }
    }
}

@Composable
private fun AppNavHost(
    appState: ArchAppState,
    modifier: Modifier,
    onFinish: () -> Unit
) {
    NavHost(
        navController = appState.navController,
        startDestination = MainDestinations.MOVIE_ROUTE,
        modifier
    ) {
        navigation(
            route = MainDestinations.MOVIE_ROUTE,
            startDestination = MainScreen.Movie.route
        ) {
            addMovieGraph(appState::navigateToMovieDetail, appState::upPress, onFinish, modifier)
        }
        navigation(
            route = MainDestinations.DISCOVERY_ROUTE,
            startDestination = MainScreen.Discovery.route
        ) {
            addDiscoveryGraph(modifier, onFinish)
        }
        navigation(
            route = MainDestinations.OTHER_ROUTE,
            startDestination = MainScreen.Other.route
        ) {
            addOtherGraph(modifier, onFinish)
        }
        navigation(
            route = MainDestinations.MINE_ROUTE,
            startDestination = MainScreen.Mine.route
        ) {
            addMineGraph(modifier, onFinish)
        }
    }
}