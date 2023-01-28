package com.example.moviefinder.db.dao

import androidx.room.*
import com.example.moviefinder.model.MovieItem


@Dao
interface FavoriteMovieItemDao {
    @Query("SELECT * FROM FavoriteMovie WHERE isFavorite == 1")
    suspend fun getMovieAll(): List<MovieItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: MovieItem)

    @Query("UPDATE FavoriteMovie SET isFavorite = 0 WHERE link =:id")
    suspend fun deleteItem(id: String)
}