package com.example.moviefinder.data.remote

import com.example.moviefinder.api.MovieFindResponse
import com.example.moviefinder.api.MovieFindService
import javax.inject.Inject

class MovieFinderRemoteDataSource @Inject constructor(
    private val service: MovieFindService){
    suspend fun getMovies(query: String, start: Int, size: Int): MovieFindResponse =
        service.getMovies(clientId = "9aBH0dVKy0Nw9QpXgQ8i", secretKey = "nW5zzTbsyc", query = query, start = start, display = size)
}