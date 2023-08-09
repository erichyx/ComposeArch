package com.eric.composearch.api


import com.eric.composearch.data.remote.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by eric on 2017/11/12.
 */


interface ApiService {
    @GET("https://frodo.douban.com/api/v2/movie/movie_showing")
    suspend fun fetchShowingMovie(
        @Query("start") start: Int,
        @Query("count") count: Int = 20,
        @Query("loc_id") locId: Int = 118201, // 厦门
        @Query("playable") playable: Int = 0,
        @Query("area") area: String = "全部",
        @Query("sort") sort: String = "recommend",
    ): MoviePagingEntity

    @GET("https://frodo.douban.com/api/v2/movie/hot_gaia")
    suspend fun fetchHotMovie(
        @Query("start") start: Int,
        @Query("count") count: Int = 20,
        @Query("playable") playable: Int = 0,
        @Query("area") area: String = "全部",
        @Query("sort") sort: String = "recommend",
    ): MoviePagingEntity

    @GET("https://frodo.douban.com/api/v2/movie/{movie_id}")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("event_source") eventSource: String = "movie_showing"
    ): MovieDetail

    @GET("https://frodo.douban.com/api/v2/movie/{movie_id}/celebrities")
    suspend fun fetchMovieCredits(
        @Path("movie_id") movieId: String,
        @Query("os_rom") osRom: String = "miui13",
        @Query("channel") channel: String = "Douban",
        @Query("timezone") timezone: String = "Asia/Shanghai",
    ): MovieCredits

    @GET("https://frodo.douban.com/api/v2/movie/{movie_id}/photos")
    suspend fun fetchMoviePhotos(
        @Path("movie_id") movieId: String,
        @Query("count") count: Int = 8,
        @Query("os_rom") osRom: String = "miui13",
        @Query("channel") channel: String = "Douban",
        @Query("timezone") timezone: String = "Asia/Shanghai",
    ): MoviePhotos

    @GET("https://frodo.douban.com/api/v2/movie/{movie_id}/related_items")
    suspend fun fetchRelatedItems(
        @Path("movie_id") movieId: String,
        @Query("count") count: Int = 10,
        @Query("os_rom") osRom: String = "miui13",
        @Query("channel") channel: String = "Douban",
        @Query("timezone") timezone: String = "Asia/Shanghai",
    ): RelatedItems
}
