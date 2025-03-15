package com.ryan.codigo2.presentation.home.state

sealed class HomeEvent {
    object LoadMovies : HomeEvent()
    object RefreshMovies : HomeEvent()
    data class ToggleFavorite(val movieId: Int, val isFavorite: Boolean) : HomeEvent()
    data class NavigateToDetail(val movieId: Int) : HomeEvent()
}