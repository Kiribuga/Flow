package com.example.flow.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.flow.R
import com.example.flow.data.AdapterMovie
import com.example.flow.databinding.FragmentSearchMoviesBinding
import com.example.flow.util.autoCleared
import com.example.flow.util.checkedChangesFlow
import com.example.flow.util.textChangesFlow

class SearchMoviesFragment : Fragment(R.layout.fragment_search_movies) {

    private val vBinding: FragmentSearchMoviesBinding by viewBinding()
    private val vmSearchMovie: ViewModelSearchMovie by viewModels()
    private var adapterSearchMovie: AdapterMovie by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observerVM()
        vmSearchMovie.bind(
            vBinding.searchEditText.textChangesFlow(),
            vBinding.typeMovieRadioGroup.checkedChangesFlow()
        )
    }

    private fun initList() {
        adapterSearchMovie = AdapterMovie()
        with(vBinding.listMovies) {
            adapter = adapterSearchMovie
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observerVM() {
        vmSearchMovie.movieListLD.observe(viewLifecycleOwner) {
            adapterSearchMovie.updateListMovies(it)
        }
        vmSearchMovie.isLoadingLD.observe(viewLifecycleOwner, ::loading)
    }

    private fun loading(result: Boolean) {
        if (result) {
            adapterSearchMovie.updateListMovies(emptyList())
            vBinding.noResultTextView.isVisible = false
        }
        vBinding.progressBar.isVisible = result
    }
}