package com.example.moviefinder.usecase

import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.model.MovieItem
import javax.inject.Inject

class GetFavoriteMovieListUseCase @Inject constructor(
    private val movieFinderRepository: MovieFinderRepository
) {
    val favoriteList = movieFinderRepository.favoriteList

    suspend operator fun invoke(): Result<List<MovieItem>> {
        return movieFinderRepository.getFavoriteMovieList()
    }
}