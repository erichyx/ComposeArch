package com.eric.composearch.ui

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eric.composearch.R
import com.eric.composearch.modules.movie.MovieDetailScreen
import com.eric.composearch.modules.movie.MovieDetailViewModel
import com.eric.composearch.modules.movie.MovieListScreen
import com.eric.composearch.modules.movie.MovieListViewModel
import com.eric.composearch.utils.APP_DEBUG_TAG
import com.eric.composearch.utils.TapBackTwiceWatcher

/**
 * Created by eric on 2022/7/5
 */

object MainDestinations {
    const val MOVIE_ROUTE = "movie_graph"
    const val DISCOVERY_ROUTE = "discovery_graph"
    const val OTHER_ROUTE = "other_graph"
    const val MINE_ROUTE = "mine_graph"

    const val MOVIE_DETAIL_ROUTE = "movieDetail"
    const val MOVIE_ID_KEY = "movie_id"
}

sealed class MainScreen(
    @StringRes val title: Int,
    val normalIcon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
) {
    object Movie : MainScreen(R.string.movie, Icons.Outlined.Movie, Icons.Filled.Movie, "movie")
    object Discovery : MainScreen(
        R.string.discovery,
        Icons.Outlined.Explore,
        Icons.Filled.Explore,
        "discovery"
    )

    object Other : MainScreen(R.string.other, Icons.Outlined.Spa, Icons.Filled.Spa, "other")
    object Mine : MainScreen(R.string.mine, Icons.Outlined.Person, Icons.Filled.Person, "mine")
}

@Composable
fun TapBackTwiceHandler(context: Context = LocalContext.current, onFinish: () -> Unit) {
    BackHandler {
        TapBackTwiceWatcher().dealBackPressed(context) {
            onFinish()
        }
    }
}

fun NavGraphBuilder.addMovieGraph(
    onMovieClicked: (String, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable(MainScreen.Movie.route) { from ->
        val movieListViewModel = hiltViewModel<MovieListViewModel>()
        MovieListScreen(
            onMovieClicked = { movieId -> onMovieClicked(movieId, from) },
            modifier,
            movieListViewModel
        )

        TapBackTwiceHandler {
            onFinish()
        }
    }
    composable(
        "${MainDestinations.MOVIE_DETAIL_ROUTE}/{${MainDestinations.MOVIE_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.MOVIE_ID_KEY) { type = NavType.StringType })
    ) { backStackEntry ->
        val movieDetailViewModel = hiltViewModel<MovieDetailViewModel>()
        val movieId = backStackEntry.arguments?.getString(MainDestinations.MOVIE_ID_KEY) ?: ""
        Log.d(APP_DEBUG_TAG, "${MainDestinations.MOVIE_ID_KEY}=$movieId")
        MovieDetailScreen(movieId, upPress, movieDetailViewModel)
    }
}

fun NavGraphBuilder.addDiscoveryGraph(modifier: Modifier = Modifier, onFinish: () -> Unit) {
    composable(MainScreen.Discovery.route) {
        Log.d(APP_DEBUG_TAG, "发现页")
        Box(
            Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            Text(text = "发现")
        }

        TapBackTwiceHandler {
            onFinish()
        }
    }
}

fun NavGraphBuilder.addOtherGraph(modifier: Modifier = Modifier, onFinish: () -> Unit) {
    composable(MainScreen.Other.route) {
        Box(
            Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            Text(text = "其他")
        }

        TapBackTwiceHandler {
            onFinish()
        }
    }
}

fun NavGraphBuilder.addMineGraph(modifier: Modifier = Modifier, onFinish: () -> Unit) {
    composable(MainScreen.Mine.route) {
        Box(
            Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            Text(text = "我的")
        }

        TapBackTwiceHandler {
            onFinish()
        }
    }
}