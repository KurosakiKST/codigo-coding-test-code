package com.ryan.codigo2.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ryan.codigo2.domain.model.Movie
import com.ryan.codigo2.presentation.common.component.ErrorMessage
import com.ryan.codigo2.presentation.common.component.LoadingIndicator
import com.ryan.codigo2.presentation.home.state.MoviesState

@Composable
fun MovieSection(
    title: String,
    moviesState: MoviesState,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int, Boolean) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (moviesState) {
            is MoviesState.Loading -> {
                LoadingIndicator(
                    modifier = Modifier.height(250.dp)
                )
            }
            is MoviesState.Success -> {
                if (moviesState.movies.isEmpty()) {
                    ErrorMessage(
                        message = "No movies found",
                        onRetry = onRetry,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(moviesState.movies) { movie ->
                            MovieItem(
                                movie = movie,
                                onMovieClick = onMovieClick,
                                onToggleFavorite = onToggleFavorite,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        }
                    }
                }
            }
            is MoviesState.Error -> {
                ErrorMessage(
                    message = moviesState.message,
                    onRetry = onRetry,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}