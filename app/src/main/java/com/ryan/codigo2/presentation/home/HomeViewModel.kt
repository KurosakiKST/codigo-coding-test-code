package com.ryan.codigo2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryan.codigo2.domain.usecase.GetPopularMoviesUseCase
import com.ryan.codigo2.domain.usecase.GetUpcomingMoviesUseCase
import com.ryan.codigo2.domain.usecase.RefreshMoviesUseCase
import com.ryan.codigo2.domain.usecase.ToggleFavoriteUseCase
import com.ryan.codigo2.presentation.home.state.HomeEvent
import com.ryan.codigo2.presentation.home.state.HomeState
import com.ryan.codigo2.presentation.home.state.MoviesState
import com.ryan.codigo2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val refreshMoviesUseCase: RefreshMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        onEvent(HomeEvent.LoadMovies)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadMovies -> loadMovies()
            is HomeEvent.RefreshMovies -> refreshMovies()
            is HomeEvent.ToggleFavorite -> toggleFavorite(event.movieId, event.isFavorite)
            else -> Unit // NavigateToDetail is handled by the UI
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            // Collect popular movies
            launch {
                getPopularMoviesUseCase().collect { movies ->
                    _state.update {
                        it.copy(popularMovies = MoviesState.Success(movies))
                    }
                }
            }

            // Collect upcoming movies
            launch {
                getUpcomingMoviesUseCase().collect { movies ->
                    _state.update {
                        it.copy(upcomingMovies = MoviesState.Success(movies))
                    }
                }
            }

            // Refresh data from API (offline-first approach)
            refreshMovies()
        }
    }

    private fun refreshMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, error = null) }

            val popularResult = refreshMoviesUseCase.refreshPopular()
            val upcomingResult = refreshMoviesUseCase.refreshUpcoming()

            if (popularResult is Resource.Error || upcomingResult is Resource.Error) {
                _state.update {
                    it.copy(
                        isRefreshing = false,
                        error = popularResult.let {
                            if (it is Resource.Error) it.message
                            else upcomingResult.let {
                                if (it is Resource.Error) it.message else null
                            }
                        }
                    )
                }
            } else {
                _state.update { it.copy(isRefreshing = false) }
            }
        }
    }

    private fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movieId, isFavorite)
        }
    }
}