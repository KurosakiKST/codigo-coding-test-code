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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ryan.codigo2.presentation.home.component.MovieSection
import com.ryan.codigo2.presentation.home.state.HomeEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
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
                onMovieClick = {
                    viewModel.onEvent(HomeEvent.NavigateToDetail(it))
                    onNavigateToDetail(it)
                },
                onToggleFavorite = { id, isFavorite ->
                    viewModel.onEvent(HomeEvent.ToggleFavorite(id, isFavorite))
                },
                onRetry = {
                    viewModel.onEvent(HomeEvent.RefreshMovies)
                }
            )

            // Upcoming Movies Section
            MovieSection(
                title = "Upcoming Movies",
                moviesState = state.upcomingMovies,
                onMovieClick = {
                    viewModel.onEvent(HomeEvent.NavigateToDetail(it))
                    onNavigateToDetail(it)
                },
                onToggleFavorite = { id, isFavorite ->
                    viewModel.onEvent(HomeEvent.ToggleFavorite(id, isFavorite))
                },
                onRetry = {
                    viewModel.onEvent(HomeEvent.RefreshMovies)
                }
            )
        }
    }
}