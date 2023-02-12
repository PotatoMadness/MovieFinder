package com.example.moviefinder.usecase

import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.model.MovieItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// id list 만 넘기는것 + 전체 리스트를 넘기는것
class GetFavoriteMovieListUseCase @Inject constructor(
    private val movieFinderRepository: MovieFinderRepository
) {
    val favoriteList = movieFinderRepository.favoriteList

    suspend operator fun invoke(): Result<List<MovieItem>> {
        return movieFinderRepository.getFavoriteMovieList()
    }
}