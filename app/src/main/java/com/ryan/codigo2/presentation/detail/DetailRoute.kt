package com.ryan.codigo2.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ryan.codigo2.presentation.detail.state.DetailEvent

@Composable
fun DetailRoute(
    movieId: Int,
    onNavigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    // Collect the state
    val state by viewModel.state.collectAsState()

    // Load movie details when movieId changes
    LaunchedEffect(movieId) {
        viewModel.onEvent(DetailEvent.LoadMovieDetails(movieId))
    }

    // Connect to the UI
    DetailScreen(
        state = state,
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        onNavigateBack = onNavigateBack
    )
}