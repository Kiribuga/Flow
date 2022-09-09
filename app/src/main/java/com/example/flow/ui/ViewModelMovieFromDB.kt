package com.example.flow.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.data.RepositoryMovie
import com.example.flow.db.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewModelMovieFromDB : ViewModel() {

    private val repository = RepositoryMovie()

    private val movieListMLD = MutableLiveData<Flow<List<Movie>>>()

    val movieListLD: LiveData<Flow<List<Movie>>>
        get() = movieListMLD

    fun showMovies() {
        viewModelScope.launch {
            try {
                movieListMLD.postValue(repository.observerMovies())
            } catch (t: Throwable) {
                Log.d("ViewModelMovieFromDB", "Error load", t)
            }
        }
    }
}