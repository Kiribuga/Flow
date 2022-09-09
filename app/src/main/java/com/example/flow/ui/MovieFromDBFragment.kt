package com.example.flow.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.flow.R
import com.example.flow.data.AdapterMovie
import com.example.flow.databinding.FragmentMovieFromDbBinding
import com.example.flow.util.autoCleared
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieFromDBFragment : Fragment(R.layout.fragment_movie_from_db) {

    private val vBinding: FragmentMovieFromDbBinding by viewBinding()
    private val vmMovie: ViewModelMovieFromDB by viewModels()
    private var adapterMovie: AdapterMovie by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observerVM()
        vmMovie.showMovies()
    }

    private fun initList() {
        adapterMovie = AdapterMovie()
        with(vBinding.listMovies) {
            adapter = adapterMovie
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observerVM() {
        vmMovie.movieListLD.observe(viewLifecycleOwner) { newFlowList ->
            lifecycleScope.launch {
                newFlowList.collect {
                    adapterMovie.updateListMovies(it)
                }
            }
        }
    }
}