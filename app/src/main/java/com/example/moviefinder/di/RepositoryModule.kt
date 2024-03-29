package com.example.moviefinder.di

import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.data.local.MovieFinderLocalDataSource
import com.example.moviefinder.data.remote.MovieFinderRemoteDataSource
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
        movieFinderRemoteDataSource: MovieFinderRemoteDataSource,
        movieFinderLocalDataSource: MovieFinderLocalDataSource
    ): MovieFinderRepository = MovieFinderRepository(movieFinderRemoteDataSource, movieFinderLocalDataSource)
}