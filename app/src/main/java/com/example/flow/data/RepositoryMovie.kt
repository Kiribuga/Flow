package com.example.flow.data

import android.util.Log
import com.example.flow.db.Database
import com.example.flow.db.movie.Movie
import com.example.flow.db.movie.MovieType
import com.example.flow.networking.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject

class RepositoryMovie {

    private val movieDao = Database.instance.movieDao()

    suspend fun searchMovies(query: String, movieType: String): List<Movie> {
        return withContext(Dispatchers.IO) {
            try {
                val movieListString = Network.omdbApi.searchMovies(query, movieType)
                val jsonObject = JSONObject(movieListString.string())
                val movieArray = jsonObject.getJSONArray("Search")
                val listMovie =
                    (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                        .map { movieJsonObject ->
                            val imdbID = movieJsonObject.getString("imdbID")
                            val title = movieJsonObject.getString("Title")
                            val typeMovie = movieJsonObject.getString("Type")
                            val poster = movieJsonObject.getString("Poster")

                            Movie(
                                id = 0L,
                                imdbID = imdbID,
                                title = title,
                                typeMovie = MovieType.valueOf(typeMovie.uppercase()),
                                poster = poster
                            )
                        }
                movieDao.insertMovies(listMovie)
                listMovie
            } catch (t: Throwable) {
                movieDao.getMoviesSearch(query, movieType).apply {
                    Log.d("Repository", "ERROR = $t")
                }
            }
        }
    }

    fun observerMovies() : Flow<List<Movie>> {
        return movieDao.observeMoviesFromBD()
    }
}