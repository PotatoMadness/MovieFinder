package com.example.moviefinder.usecase

import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.model.MovieItem

class UpdateFavoriteMovieUseCase constructor(
    private val movieFinderRepository: MovieFinderRepository
) {
    suspend operator fun invoke(item: MovieItem): Result<Any> {
        return if (item.isFavorite) movieFinderRepository.deleteFavoriteMovie(item)
        else movieFinderRepository.saveFavoriteMovie(item)
    }
}