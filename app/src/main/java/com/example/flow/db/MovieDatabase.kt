package com.example.flow.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flow.db.movie.Movie
import com.example.flow.db.movie.MovieDao
import com.example.flow.db.movie.MovieTypeConverter

@Database(
    entities = [Movie::class],
    version = MovieDatabase.DB_VERSION
)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "movie_imdb"
    }
}