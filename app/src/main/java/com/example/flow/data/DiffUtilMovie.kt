package com.example.flow.data

import androidx.recyclerview.widget.DiffUtil
import com.example.flow.db.movie.Movie

class DiffUtilMovie : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}