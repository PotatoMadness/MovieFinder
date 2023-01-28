package com.example.moviefinder.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviefinder.model.MovieItem

class FavoriteMoviePagingSource(
    private val movieFinderRepository: MovieFinderRepository
) : PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(defaultDisplay)
                ?: closestPageToPosition?.nextKey?.minus(defaultDisplay)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        val start = params.key ?: defaultStart

        return try {
            val result = movieFinderRepository.getFavoriteMovieList()

            val items = result.getOrThrow()
            val nextKey = if (items.size < params.loadSize) null
                else start + params.loadSize

            val prevKey = if (start == defaultStart) null
                else start - defaultDisplay

            LoadResult.Page(items, prevKey, nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val defaultStart = 1
        const val defaultDisplay = 10
    }
}