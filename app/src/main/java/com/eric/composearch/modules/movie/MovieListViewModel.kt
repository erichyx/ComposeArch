package com.eric.composearch.modules.movie

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eric.composearch.data.remote.MovieItem
import com.eric.composearch.data.remote.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by eric on 2022/5/7
 */
@Stable
data class MovieListUiState(
    val movieTypeIndex: Int = 0,
    val sortTypeIndex: Int = 0,
    val sortType: String = "recommend"
)

private val MovieListUiState.showingMovie: Boolean
    get() = movieTypeIndex == 0


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val remoteRepo: MovieRepo
) : ViewModel() {
    var uiState by mutableStateOf(MovieListUiState())
        private set

    val movieListData: Flow<PagingData<MovieItem>>
        get() = _movieListData

    private var _movieListData =
        remoteRepo.getMoviePagingStream(uiState.showingMovie, uiState.sortType)
            .cachedIn(viewModelScope)

    fun updateMovieTypeIndex(movieTypeIndex: Int) {
        if (uiState.movieTypeIndex != movieTypeIndex) {
            uiState = uiState.copy(movieTypeIndex = movieTypeIndex)
            _movieListData =
                remoteRepo.getMoviePagingStream(uiState.showingMovie, uiState.sortType)
                    .cachedIn(viewModelScope)
        }
    }

    fun updateMovieSortType(sortTypeIndex: Int, sortType: String) {
        if (uiState.sortTypeIndex != sortTypeIndex) {
            uiState = uiState.copy(sortTypeIndex = sortTypeIndex, sortType = sortType)
            _movieListData =
                remoteRepo.getMoviePagingStream(uiState.showingMovie, uiState.sortType)
                    .cachedIn(viewModelScope)
        }
    }
}