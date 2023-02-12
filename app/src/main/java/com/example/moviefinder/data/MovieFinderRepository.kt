package com.example.moviefinder.data

import com.example.moviefinder.data.local.MovieFinderLocalDataSource
import com.example.moviefinder.data.remote.MovieFinderRemoteDataSource
import com.example.moviefinder.model.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow

class MovieFinderRepository(
    private val remoteDataSource: MovieFinderRemoteDataSource,
    private val localDataSource: MovieFinderLocalDataSource) {

    val favoriteList = MutableStateFlow<List<MovieItem>>(arrayListOf())

    suspend fun getMovies(query: String, start: Int, size: Int): Result<List<MovieItem>> {
        return try {
            val remoteMovie = remoteDataSource.getMovies(query, start, size)
            Result.success(remoteMovie.items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFavoriteMovieList(): Result<List<MovieItem>> {
        kotlin.runCatching {
            localDataSource.getFavoriteList()
        }.onSuccess {
            favoriteList.emit(it)
            return Result.success(it)
        }.onFailure {
            return Result.failure(it)
        }.also {
            return Result.failure(Exception("DB Error"))
        }
    }

    suspend fun saveFavoriteMovie(movieItem: MovieItem): Result<List<MovieItem>> {
        kotlin.runCatching {
            localDataSource.addFavoriteMovie(movieItem)
        }.onSuccess {
            return getFavoriteMovieList()
        }.onFailure {
            return Result.failure(it)
        }.also {
            return Result.failure(Exception("DB Error"))
        }
    }

    suspend fun deleteFavoriteMovie(movieItem: MovieItem): Result<List<MovieItem>> {
        kotlin.runCatching {
            localDataSource.deleteFavoriteMovie(movieItem)
        }.onSuccess {
            return getFavoriteMovieList()
        }.onFailure {
            return Result.failure(it)
        }.also {
            return Result.failure(Exception("DB Error"))
        }
    }
}