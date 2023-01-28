package com.example.moviefinder.di

import android.content.Context
import androidx.room.Room
import com.example.moviefinder.db.AppDatabase
import com.example.moviefinder.db.dao.FavoriteMovieItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "movie_finder"

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesFavoriteMovieItemDao(appDatabase: AppDatabase): FavoriteMovieItemDao = appDatabase.favoriteMovieItemDao()
}