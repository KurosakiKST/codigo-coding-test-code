package com.ryan.codigo2.data.repository

import com.ryan.codigo2.data.local.dao.MovieDao
import com.ryan.codigo2.data.mapper.toMovie
import com.ryan.codigo2.data.mapper.toMovieEntity
import com.ryan.codigo2.data.remote.api.MovieApi
import com.ryan.codigo2.domain.model.Movie
import com.ryan.codigo2.domain.repository.MovieRepository
import com.ryan.codigo2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao,
    private val apiKey: String
) : MovieRepository {

    override fun getPopularMovies(): Flow<List<Movie>> {
        return dao.getMoviesByType("popular")
            .map { entities -> entities.map { it.toMovie() } }
    }

    override fun getUpcomingMovies(): Flow<List<Movie>> {
        return dao.getMoviesByType("upcoming")
            .map { entities -> entities.map { it.toMovie() } }
    }

    override fun getMovieById(id: Int): Flow<Movie?> {
        return dao.getMovieById(id).map { it?.toMovie() }
    }

    override suspend fun refreshPopularMovies(): Resource<Unit> {
        return try {
            val response = api.getPopularMovies(apiKey)
            val movies = response.results.map { it.toMovieEntity("popular") }
            dao.insertMovies(movies)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Couldn't refresh popular movies: ${e.localizedMessage}")
        }
    }

    override suspend fun refreshUpcomingMovies(): Resource<Unit> {
        return try {
            val response = api.getUpcomingMovies(apiKey)
            val movies = response.results.map { it.toMovieEntity("upcoming") }
            dao.insertMovies(movies)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Couldn't refresh upcoming movies: ${e.localizedMessage}")
        }
    }

    override suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        dao.updateFavorite(id, isFavorite)
    }
}