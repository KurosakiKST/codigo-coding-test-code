package com.ryan.codigo2.presentation.detail.state

sealed class DetailEvent {
    data class LoadMovieDetails(val movieId: Int) : DetailEvent()
    data class ToggleFavorite(val isFavorite: Boolean) : DetailEvent()
}