package com.ryan.codigo2.presentation.home.state

import com.ryan.codigo2.domain.model.Movie

data class HomeState(
    val popularMovies: MoviesState = MoviesState.Loading,
    val upcomingMovies: MoviesState = MoviesState.Loading,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

sealed class MoviesState {
    object Loading : MoviesState()
    data class Success(val movies: List<Movie>) : MoviesState()
    data class Error(val message: String) : MoviesState()
}