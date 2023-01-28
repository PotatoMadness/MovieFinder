package com.example.moviefinder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviefinder.db.dao.FavoriteMovieItemDao
import com.example.moviefinder.model.MovieItem

@Database(entities = [MovieItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieItemDao() : FavoriteMovieItemDao
}