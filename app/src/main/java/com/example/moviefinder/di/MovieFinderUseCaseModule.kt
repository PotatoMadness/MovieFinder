package com.example.moviefinder.di

import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.usecase.FindMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieFinderUseCaseModule {
    @Singleton
    @Provides
    fun provideFindMovieUseCase(movieFinderRepository: MovieFinderRepository): FindMovieUseCase =
        FindMovieUseCase(movieFinderRepository)
}