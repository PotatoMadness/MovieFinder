package com.example.moviefinder.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviefinder.data.MovieFinderPagingSource
import com.example.moviefinder.data.MovieFinderRepository
import com.example.moviefinder.model.MovieItem
import kotlinx.coroutines.flow.Flow

class FindMovieUseCase constructor(
    private val movieFinderRepository: MovieFinderRepository
) {
    operator fun invoke(query: String): Flow<PagingData<MovieItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieFinderPagingSource(query, movieFinderRepository) }
        ).flow
}