package com.ryan.codigo2.data.local.dao

import androidx.room.*
import com.ryan.codigo2.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE type = :type ORDER BY popularity DESC")
    fun getMoviesByType(type: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}