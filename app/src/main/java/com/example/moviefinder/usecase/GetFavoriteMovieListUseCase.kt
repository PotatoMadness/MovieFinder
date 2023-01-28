package com.example.moviefinder.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviefinder.data.FavoriteMoviePagingSource
import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.model.MovieItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMovieListUseCase @Inject constructor(
    private val movieFinderRepository: MovieFinderRepository
) {
    operator fun invoke(): Flow<PagingData<MovieItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FavoriteMoviePagingSource(movieFinderRepository) }
        ).flow
}