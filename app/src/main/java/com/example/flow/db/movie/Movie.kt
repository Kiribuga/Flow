package com.example.flow.db.movie

import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(
    tableName = MovieContracts.TABLE_NAME,
    indices = [Index(MovieContracts.Columns.ID_IMDB, unique = true)]
)
@TypeConverters(MovieTypeConverter::class)
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MovieContracts.Columns.ID)
    val id: Long,
    @ColumnInfo(name = MovieContracts.Columns.ID_IMDB)
    val imdbID: String,
    @ColumnInfo(name = MovieContracts.Columns.TITLE)
    @Json(name = "Title")
    val title: String,
    @ColumnInfo(name = MovieContracts.Columns.TYPE_MOVIE)
    @Json(name = "Type")
    val typeMovie: MovieType,
    @ColumnInfo(name = MovieContracts.Columns.POSTER)
    @Json(name = "Poster")
    val poster: String
)
