package com.ryan.codigo2.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryan.codigo2.domain.usecase.GetMovieDetailsUseCase
import com.ryan.codigo2.domain.usecase.ToggleFavoriteUseCase
import com.ryan.codigo2.presentation.detail.state.DetailEvent
import com.ryan.codigo2.presentation.detail.state.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.LoadMovieDetails -> loadMovieDetails(event.movieId)
            is DetailEvent.ToggleFavorite -> toggleFavorite(event.isFavorite)
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getMovieDetailsUseCase(movieId).collect { movie ->
                if (movie != null) {
                    _state.update {
                        it.copy(
                            movie = movie,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Movie not found"
                        )
                    }
                }
            }
        }
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            val movie = state.value.movie ?: return@launch
            toggleFavoriteUseCase(movie.id, isFavorite)
            _state.update {
                it.copy(movie = movie.copy(isFavorite = isFavorite))
            }
        }
    }
}