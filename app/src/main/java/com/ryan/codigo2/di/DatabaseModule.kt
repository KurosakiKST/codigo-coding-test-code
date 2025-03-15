package com.ryan.codigo2.di

import android.app.Application
import androidx.room.Room
import com.ryan.codigo2.data.local.dao.MovieDao
import com.ryan.codigo2.data.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao
    }
}