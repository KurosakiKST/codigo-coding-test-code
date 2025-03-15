package com.ryan.codigo2.domain.usecase

import com.ryan.codigo2.domain.model.Movie
import com.ryan.codigo2.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getPopularMovies()
    }
}

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getUpcomingMovies()
    }
}

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(id: Int): Flow<Movie?> {
        return repository.getMovieById(id)
    }
}

class RefreshMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun refreshPopular() = repository.refreshPopularMovies()
    suspend fun refreshUpcoming() = repository.refreshUpcomingMovies()
}

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) {
        repository.toggleFavorite(id, isFavorite)
    }
}