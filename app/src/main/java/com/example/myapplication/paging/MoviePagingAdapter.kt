package com.example.myapplication.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.MovieItemBinding
import com.example.myapplication.model.MovieList
import com.example.myapplication.model.Result
import com.example.myapplication.utils.Constants.POSTER_URL

class MoviePagingAdapter(private val onItemClicked:(Result)->Unit) : PagingDataAdapter<Result, MoviePagingAdapter.MovieViewHolder>(COMPARATOR) {
   inner class MovieViewHolder(var binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
              fun bind(movieItem: Result){
                  binding.apply {
                      releaseDate.text = movieItem.release_date
                      title.text = movieItem.title
                      voteAverage.text = movieItem.vote_average.toString()
                      val url = POSTER_URL + movieItem?.poster_path
                      // Glide.with(context).load(url).into(thumbNail)
                      thumbNail.load(url) {
                          crossfade(true)
                          placeholder(R.drawable.ic_launcher_foreground)
                          scale(Scale.FILL)
                      }
                      root.setOnClickListener {
                          onItemClicked(movieItem)

                      }
                  }
              }

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
      /*  var movieItem = getItem(position)
        if (movieItem != null) {
            holder.binding.apply {
                releaseDate.text = movieItem.release_date
                title.text = movieItem.title
                voteAverage.text = movieItem.vote_average.toString()
                val url = POSTER_URL + movieItem?.poster_path
                // Glide.with(context).load(url).into(thumbNail)
                thumbNail.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                    scale(Scale.FILL)
                }
            }
        }*/
        val movie = getItem(position)
        movie?.let{
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object{
        private val COMPARATOR = object :DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
              return oldItem == newItem
            }

        }
    }
}