package com.ryan.codigo2.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ryan.codigo2.domain.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .clickable { onMovieClick(movie.id) }
    ) {
        Column {
            Box {
                AsyncImage(
                    model = movie.getPosterUrl(),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .height(225.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { onToggleFavorite(movie.id, !movie.isFavorite) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (movie.isFavorite) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle Favorite",
                        tint = if (movie.isFavorite) Color.Red else Color.White
                    )
                }
            }

            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}