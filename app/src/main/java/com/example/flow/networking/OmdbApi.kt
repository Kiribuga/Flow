package com.example.flow.networking

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("type") typeMovie: String
    ) : ResponseBody
}