package com.example.moviefinder.data

import com.example.moviefinder.api.MovieFindService
import com.example.moviefinder.model.MovieItem

class MovieFinderRepository(
    private val service: MovieFindService) {
    suspend fun getMovies(query: String, start: Int, size: Int): Result<List<MovieItem>> {
        kotlin.runCatching {
            service.getMovies(clientId = "9aBH0dVKy0Nw9QpXgQ8i", secretKey = "nW5zzTbsyc", query = query, start = start, display = size)
        }.onSuccess {
            return Result.success(it.items)
        }.onFailure {
            return Result.failure(Throwable())
        }.also {
            return Result.failure(Throwable())
        }
    }
}