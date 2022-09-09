package com.example.flow.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.flow.R
import com.example.flow.databinding.MainFragmentBinding

class FragmentMain : Fragment(R.layout.main_fragment) {

    private val vBinding: MainFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vBinding.searchMovieButton.setOnClickListener {
            findNavController()
                .navigate(FragmentMainDirections.actionFragmentMainToSearchMoviesFragment())
        }
        vBinding.showMoviesFromDBButton.setOnClickListener {
            findNavController()
                .navigate(FragmentMainDirections.actionFragmentMainToMovieFromDBFragment())
        }
    }
}