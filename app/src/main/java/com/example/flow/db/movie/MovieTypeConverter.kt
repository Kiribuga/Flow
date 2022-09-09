package com.example.flow.db.movie

import androidx.room.TypeConverter

class MovieTypeConverter {

    @TypeConverter
    fun convertToString(type: MovieType): String = type.name

    @TypeConverter
    fun convertToMovieType(typeString: String): MovieType = MovieType.valueOf(typeString)
}