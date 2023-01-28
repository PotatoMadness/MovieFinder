package com.example.moviefinder.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteMovie")
data class MovieItem(
    val title: String,
    @PrimaryKey
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: String,
    var isFavorite: Boolean = false
)