package com.eric.composearch.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eric.composearch.api.ApiService
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by eric on 2022/5/7
 */
private const val STARTING_PAGE_INDEX = 0

class MoviePagingSource(private val api: ApiService, private val isShowing:Boolean, private val sort : String) : PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        // params.key=null时，说明是第一次加载
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = if (isShowing) {
                api.fetchShowingMovie(position, params.loadSize, sort=sort)
            } else {
                api.fetchHotMovie(position, params.loadSize, sort=sort)
            }
            val repos = response.movieItems
            val loadedSize = response.start + repos.size
            val nextKey = if (loadedSize >= response.total) {
                null
            } else {
                loadedSize
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - params.loadSize,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}