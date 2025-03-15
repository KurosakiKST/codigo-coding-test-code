package com.ryan.codigo2.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ryan.codigo2.presentation.common.component.ErrorMessage
import com.ryan.codigo2.presentation.common.component.LoadingIndicator
import com.ryan.codigo2.presentation.detail.state.DetailEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    onNavigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.onEvent(DetailEvent.LoadMovieDetails(movieId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.movie?.title ?: "Movie Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    state.movie?.let { movie ->
                        IconButton(
                            onClick = {
                                viewModel.onEvent(DetailEvent.ToggleFavorite(!movie.isFavorite))
                            }
                        ) {
                            Icon(
                                imageVector = if (movie.isFavorite)
                                    Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Toggle Favorite",
                                tint = if (movie.isFavorite) Color.Red else Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    LoadingIndicator()
                }
                state.error != null -> {
                    ErrorMessage(
                        message = state.error ?: "An error occurred",
                        onRetry = {
                            viewModel.onEvent(DetailEvent.LoadMovieDetails(movieId))
                        }
                    )
                }
                state.movie != null -> {
                    val movie = state.movie!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Backdrop Image
                        AsyncImage(
                            model = movie.getBackdropUrl(),
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f),
                            contentScale = ContentScale.Crop
                        )

                        // Movie Details
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Release Date: ${movie.releaseDate ?: "Unknown"}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Rating: ${movie.voteAverage}/10",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Overview",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = movie.overview,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}