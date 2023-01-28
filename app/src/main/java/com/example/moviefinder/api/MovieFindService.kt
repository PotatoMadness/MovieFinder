package com.example.moviefinder.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieFindService {
    @GET("/v1/search/movie.json")
    suspend fun getMovies(
        @Header("X-Naver-Client-Id") clientId:String,
        @Header("X-Naver-Client-Secret") secretKey:String,
        @Query("query") query: String,
        @Query("display") display: Int? = null,
        @Query("start") start: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("genre") genre: String = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,22,23,24,25"
    ): MovieFindResponse
}