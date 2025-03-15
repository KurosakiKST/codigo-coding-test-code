package com.ryan.codigo2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val popularity: Double,
    val type: String, // "popular" or "upcoming"
    val isFavorite: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)