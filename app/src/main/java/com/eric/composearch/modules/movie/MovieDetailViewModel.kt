package com.eric.composearch.modules.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eric.composearch.data.remote.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by eric on 2022/5/7
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val remoteRepo: MovieRepo
) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?>
        get() = _movieDetail

    fun fetchMovieDetail(movieId: String) = viewModelScope.launch {
        _movieDetail.value = remoteRepo.fetchMovieDetail(movieId)
    }

    private val _movieCredits = MutableStateFlow<MovieCredits?>(null)
    val movieCredits: StateFlow<MovieCredits?>
        get() = _movieCredits

    fun fetchMovieCredits(movieId: String) = viewModelScope.launch {
        _movieCredits.value = remoteRepo.fetchMovieCredits(movieId)
    }

    private val _moviePhotos = MutableStateFlow<MoviePhotos?>(null)
    val moviePhotos: StateFlow<MoviePhotos?>
        get() = _moviePhotos

    fun fetchMoviePhotos(movieId: String) = viewModelScope.launch {
        _moviePhotos.value = remoteRepo.fetchMoviePhotos(movieId)
    }

    private val m_relatedItems = MutableStateFlow<RelatedItems?>(null)
    val relatedItems: StateFlow<RelatedItems?>
        get() = m_relatedItems

    fun fetchRelatedItems(movieId: String) = viewModelScope.launch {
        m_relatedItems.value = remoteRepo.fetchRelatedItems(movieId)
    }

}