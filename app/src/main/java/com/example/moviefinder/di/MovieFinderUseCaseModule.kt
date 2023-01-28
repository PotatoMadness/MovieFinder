package com.example.moviefinder.di

import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.usecase.FindMovieUseCase
import com.example.moviefinder.usecase.GetFavoriteMovieListUseCase
import com.example.moviefinder.usecase.UpdateFavoriteMovieUseCase
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

    @Singleton
    @Provides
    fun provideUpdateFavoriteMovieUseCase(movieFinderRepository: MovieFinderRepository): UpdateFavoriteMovieUseCase =
        UpdateFavoriteMovieUseCase(movieFinderRepository)

    @Singleton
    @Provides
    fun provideUpdateGetFavoriteMovieListUseCase(movieFinderRepository: MovieFinderRepository): GetFavoriteMovieListUseCase =
        GetFavoriteMovieListUseCase(movieFinderRepository)
}