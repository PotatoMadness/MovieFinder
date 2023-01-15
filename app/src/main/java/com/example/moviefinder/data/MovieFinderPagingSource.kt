package com.example.moviefinder.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviefinder.model.MovieItem

class MovieFinderPagingSource(
    private val query: String,
    private val movieFinderRepository: MovieFinderRepository
) : PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(defaultDisplay)
                ?: closestPageToPosition?.nextKey?.minus(defaultDisplay)
        }
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, MovieItem> {
        val start = params.key ?: defaultStart

        return try {
            val result = movieFinderRepository.getMovies(query, start, params.loadSize)
            if (result.isFailure) throw Exception("찾을수 없습니다")

            val items = result.getOrThrow()
            val nextKey = if (items.isEmpty()) {
                null
            } else {
                start + params.loadSize
            }
            val prevKey = if (start == defaultStart) {
                null
            } else {
                start - defaultDisplay
            }
            PagingSource.LoadResult.Page(items, prevKey, nextKey)
        } catch (exception: Exception) {
            PagingSource.LoadResult.Error(exception)
        }
    }

    companion object {
        const val defaultStart = 1
        const val defaultDisplay = 10
    }
}