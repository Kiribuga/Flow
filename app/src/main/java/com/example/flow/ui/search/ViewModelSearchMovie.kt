package com.example.flow.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.data.RepositoryMovie
import com.example.flow.db.movie.Movie
import com.example.flow.db.movie.MovieType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ViewModelSearchMovie : ViewModel() {

    private val repository = RepositoryMovie()
    private val movieListMLD = MutableLiveData<List<Movie>>()
    private val isLoadingMLD = MutableLiveData<Boolean>()

    val movieListLD: LiveData<List<Movie>>
        get() = movieListMLD
    val isLoadingLD: LiveData<Boolean>
        get() = isLoadingMLD

    private var job: Job? = null

    fun bind(queryFlow: Flow<String>, movieTypeFlow: Flow<MovieType>) {
        viewModelScope.launch {
            try {
                job = combine(
                    queryFlow
                        .onStart { emit("") }
                        .filter { it.isNotEmpty() && it.length > 2 },
                    movieTypeFlow
                        .onStart { emit(MovieType.MOVIE) }
                        .map { it.toString() }
                ) { query, movieType -> query to movieType }
                    .debounce(1000)
                    .distinctUntilChanged()
                    .onEach { isLoadingMLD.postValue(true) }
                    .mapLatest { (query, movieType) -> repository.searchMovies(query, movieType) }
                    .onEach {
                        isLoadingMLD.postValue(false)
                        movieListMLD.postValue(it)
                    }
                    .launchIn(viewModelScope)
            } catch (t: Throwable) {
                Log.d("ViewModelSearchMovie", "Error load", t)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}