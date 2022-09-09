package com.example.flow.data

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flow.R
import com.example.flow.db.movie.Movie
import com.example.flow.db.movie.MovieType
import com.example.flow.util.inflate

class AdapterMovie : RecyclerView.Adapter<AdapterMovie.MovieHolder>() {

    private val mDiffer: AsyncListDiffer<Movie> = AsyncListDiffer(this, DiffUtilMovie())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = mDiffer.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    fun updateListMovies(newList: List<Movie>) {
        mDiffer.submitList(newList)
    }

    abstract class BaseHolder(
        private val containerView: View
    ) : RecyclerView.ViewHolder(containerView) {

        private val posterMovie: ImageView = itemView.findViewById(R.id.posterMovie)
        private val titleMovie: TextView = itemView.findViewById(R.id.titleMovie)
        private val typeMovie: TextView = itemView.findViewById(R.id.typeMovie)
        private val idImdbMovie: TextView = itemView.findViewById(R.id.idIMDBMovie)

        protected fun bindMainInfo(
            poster: String,
            title: String,
            type: MovieType,
            idImdb: String
        ) {
            titleMovie.text = title
            typeMovie.text = type.toString()
            idImdbMovie.text = idImdb

            Glide.with(itemView)
                .load(poster)
                .placeholder(R.drawable.ic_movies)
                .into(posterMovie)
        }
    }

    class MovieHolder(
        containerView: View
    ) : BaseHolder(containerView) {
        fun bind(movie: Movie) {
            bindMainInfo(movie.poster, movie.title, movie.typeMovie, movie.imdbID)
        }
    }
}