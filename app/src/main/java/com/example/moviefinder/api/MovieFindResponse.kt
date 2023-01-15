package com.example.moviefinder.api

import com.example.moviefinder.model.MovieItem

data class MovieFindResponse(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<MovieItem>
)