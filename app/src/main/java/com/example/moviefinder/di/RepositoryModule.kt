package com.example.moviefinder.di

import android.content.SharedPreferences
import com.example.moviefinder.api.MovieFindService
import com.example.moviefinder.data.MovieFinderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieFinderRepository(
        movieFindService: MovieFindService
    ): MovieFinderRepository = MovieFinderRepository(movieFindService)
}