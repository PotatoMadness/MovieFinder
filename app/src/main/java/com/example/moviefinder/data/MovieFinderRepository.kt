package com.example.moviefinder.data

import com.example.moviefinder.data.local.MovieFinderLocalDataSource
import com.example.moviefinder.data.remote.MovieFinderRemoteDataSource
import com.example.moviefinder.model.MovieItem

class MovieFinderRepository(
    private val remoteDataSource: MovieFinderRemoteDataSource,
    private val localDataSource: MovieFinderLocalDataSource) {

    suspend fun getMovies(query: String, start: Int, size: Int): Result<List<MovieItem>> {
        return try {
            val remoteMovie = remoteDataSource.getMovies(query, start, size)
            val localFavMovie = localDataSource.getFavoriteList()
            remoteMovie.items.forEach { rm ->
                val finded = localFavMovie.find { it.link == rm.link }
                if (finded != null) rm.isFavorite = true
            }
            Result.success(remoteMovie.items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFavoriteMovieList(): Result<List<MovieItem>> {
        kotlin.runCatching {
            localDataSource.getFavoriteList()
        }.onSuccess {
            return Result.success(it)
        }.onFailure {
            return Result.failure(it)
        }.also {
            return Result.failure(Exception("DB Error"))
        }
    }

    suspend fun saveFavoriteMovie(movieItem: MovieItem): Result<Any> {
        kotlin.runCatching {
            localDataSource.addFavoriteMovie(movieItem.copy(isFavorite = true))
        }.onSuccess {
            return Result.success(it)
        }.onFailure {
            return Result.failure(it)
        }.also {
            return Result.failure(Exception("DB Error"))
        }
    }

    suspend fun deleteFavoriteMovie(movieItem: MovieItem): Result<Any> {
        kotlin.runCatching {
            localDataSource.deleteFavoriteMovie(movieItem)
        }.onSuccess {
            return Result.success(it)
        }.onFailure {
            return Result.failure(it)
        }.also {
            return Result.failure(Exception("DB Error"))
        }
    }
}