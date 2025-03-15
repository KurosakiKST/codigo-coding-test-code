package com.ryan.codigo2.data.remote.api

import com.ryan.codigo2.data.remote.dto.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String
    ): MovieListResponse
}