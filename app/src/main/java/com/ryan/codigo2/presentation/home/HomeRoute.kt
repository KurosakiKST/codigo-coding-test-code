package com.ryan.codigo2.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        state = state,
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        onNavigateToDetail = onNavigateToDetail
    )
}