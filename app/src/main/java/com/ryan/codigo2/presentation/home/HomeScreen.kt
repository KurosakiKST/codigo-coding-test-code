package com.ryan.codigo2.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ryan.codigo2.presentation.home.component.MovieSection
import com.ryan.codigo2.presentation.home.state.HomeEvent
import com.ryan.codigo2.presentation.home.state.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Show error in Snackbar if any
    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movies App") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Popular Movies Section
            MovieSection(
                title = "Popular Movies",
                moviesState = state.popularMovies,
                onMovieClick = { movieId ->
                    onEvent(HomeEvent.NavigateToDetail(movieId))
                    onNavigateToDetail(movieId)
                },
                onToggleFavorite = { id, isFavorite ->
                    onEvent(HomeEvent.ToggleFavorite(id, isFavorite))
                },
                onRetry = {
                    onEvent(HomeEvent.RefreshMovies)
                }
            )

            // Upcoming Movies Section
            MovieSection(
                title = "Upcoming Movies",
                moviesState = state.upcomingMovies,
                onMovieClick = { movieId ->
                    onEvent(HomeEvent.NavigateToDetail(movieId))
                    onNavigateToDetail(movieId)
                },
                onToggleFavorite = { id, isFavorite ->
                    onEvent(HomeEvent.ToggleFavorite(id, isFavorite))
                },
                onRetry = {
                    onEvent(HomeEvent.RefreshMovies)
                }
            )
        }
    }
}