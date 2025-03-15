package com.ryan.codigo2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    val popularity: Double
)

data class MovieListResponse(
    val results: List<MovieDto>,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)