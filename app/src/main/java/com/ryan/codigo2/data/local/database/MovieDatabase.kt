package com.ryan.codigo2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ryan.codigo2.data.local.dao.MovieDao
import com.ryan.codigo2.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}