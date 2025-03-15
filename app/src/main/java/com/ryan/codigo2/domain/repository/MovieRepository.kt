package com.ryan.codigo2.domain.repository

import com.ryan.codigo2.domain.model.Movie
import com.ryan.codigo2.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<List<Movie>>
    fun getUpcomingMovies(): Flow<List<Movie>>
    fun getMovieById(id: Int): Flow<Movie?>

    suspend fun refreshPopularMovies(): Resource<Unit>
    suspend fun refreshUpcomingMovies(): Resource<Unit>
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
}