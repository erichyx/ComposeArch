package com.eric.composearch.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eric.composearch.api.ApiService
import com.eric.composearch.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by eric on 2022/5/6
 */
@Singleton
class MovieRepo @Inject constructor(private val api: ApiService) {
    suspend fun fetchMovieDetail(movieId: String) = safeApiCall {
        api.fetchMovieDetail(movieId)
    }

    suspend fun fetchMovieCredits(movieId: String) = safeApiCall {
        api.fetchMovieCredits(movieId)
    }

    suspend fun fetchMoviePhotos(movieId: String) = safeApiCall {
        api.fetchMoviePhotos(movieId)
    }

    suspend fun fetchRelatedItems(movieId: String) = safeApiCall {
        api.fetchRelatedItems(movieId)
    }

    fun getMoviePagingStream(
        isShowing: Boolean,
        sort: String = "recommend"
    ): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { MoviePagingSource(api, isShowing, sort) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}