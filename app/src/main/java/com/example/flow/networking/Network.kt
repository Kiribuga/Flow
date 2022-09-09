package com.example.flow.networking

import com.example.flow.db.movie.Movie
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomInterceptor("apikey", API_KEY))
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com")
        .client(client)
        .build()

    val omdbApi: OmdbApi
        get() = retrofit.create()
}