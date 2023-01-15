package com.example.moviefinder.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieFindService {
    @GET("/v1/search/movie.json")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("display") display: Int? = null,
        @Query("start") start: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("genre") genre: String? = null
    ): MovieFindResponse
}