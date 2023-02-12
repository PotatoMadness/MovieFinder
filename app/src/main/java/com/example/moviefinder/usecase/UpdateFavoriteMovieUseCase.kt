package com.example.moviefinder.usecase

import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.model.MovieItem

class UpdateFavoriteMovieUseCase constructor(
    private val movieFinderRepository: MovieFinderRepository
) {
    suspend operator fun invoke(isFavorite: Boolean, item: MovieItem): Result<List<MovieItem>> {
        return if (isFavorite) movieFinderRepository.deleteFavoriteMovie(item)
        else movieFinderRepository.saveFavoriteMovie(item)
    }
}