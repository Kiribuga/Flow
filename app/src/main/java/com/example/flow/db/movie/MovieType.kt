package com.example.flow.db.movie

import com.squareup.moshi.Json

enum class MovieType {
    @Json(name = "movie")
    MOVIE,
    @Json(name = "series")
    SERIES,
    @Json(name = "episode")
    EPISODE
}