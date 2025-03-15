package com.ryan.codigo2.data.mapper

import com.ryan.codigo2.data.local.entity.MovieEntity
import com.ryan.codigo2.data.remote.dto.MovieDto
import com.ryan.codigo2.domain.model.Movie

fun MovieDto.toMovieEntity(type: String): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        popularity = popularity,
        type = type
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        popularity = popularity,
        isFavorite = isFavorite
    )
}