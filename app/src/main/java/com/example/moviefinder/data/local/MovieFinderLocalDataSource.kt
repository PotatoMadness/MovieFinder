package com.example.moviefinder.data.local

import com.example.moviefinder.db.dao.FavoriteMovieItemDao
import com.example.moviefinder.model.MovieItem
import javax.inject.Inject

class MovieFinderLocalDataSource @Inject constructor(
    private val favoriteMovieItemDao: FavoriteMovieItemDao
) {
    suspend fun getFavoriteList(): List<MovieItem> = favoriteMovieItemDao.getMovieAll()
    suspend fun addFavoriteMovie(movieItem: MovieItem) = favoriteMovieItemDao.addItem(movieItem)
    suspend fun deleteFavoriteMovie(movieItem: MovieItem) = favoriteMovieItemDao.deleteItem(movieItem.link)
}