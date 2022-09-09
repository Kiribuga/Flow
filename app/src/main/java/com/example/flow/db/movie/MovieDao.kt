package com.example.flow.db.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM ${MovieContracts.TABLE_NAME} WHERE title LIKE '%' || :query || ' %' AND typeMovie = :typeMovie")
    suspend fun getMoviesSearch(query: String, typeMovie: String): List<Movie>

    @Query("SELECT * FROM ${MovieContracts.TABLE_NAME}")
    fun observeMoviesFromBD(): Flow<List<Movie>>
}