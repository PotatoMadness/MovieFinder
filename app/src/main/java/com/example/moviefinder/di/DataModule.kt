package com.example.moviefinder.di

import com.example.moviefinder.api.MovieFindService
import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.data.remote.MovieFinderRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideMovieFinderRemoteDataSource(
        movieFindService: MovieFindService
    ): MovieFinderRemoteDataSource = MovieFinderRemoteDataSource(movieFindService)
}