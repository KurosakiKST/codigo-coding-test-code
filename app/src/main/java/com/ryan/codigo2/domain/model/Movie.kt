// domain/model/Movie.kt
package com.ryan.codigo2.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val popularity: Double,
    val isFavorite: Boolean = false
) {
    fun getPosterUrl(): String = "https://image.tmdb.org/t/p/w500$posterPath"
    fun getBackdropUrl(): String = "https://image.tmdb.org/t/p/w500$backdropPath"
}