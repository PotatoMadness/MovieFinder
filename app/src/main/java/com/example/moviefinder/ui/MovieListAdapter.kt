package com.example.moviefinder.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviefinder.databinding.ListMovieItemBinding
import com.example.moviefinder.model.MovieItem

class MovieListAdapter
    : PagingDataAdapter<MovieItem, MovieListAdapter.ViewHolder>(DIFF_ITEM_STATION_DATA) {

    class ViewHolder(val binding: ListMovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            getItem(position)?.let { item ->

                Glide.with(holder.itemView.context).load(item.image)
                    .into(imgMovieItem)
                tvTitle.text = item.title
                tvDirector.text = item.director
                tvActors.text = item.actor
                tvRate.text = item.userRating
            }
        }
    }

    companion object {
        val DIFF_ITEM_STATION_DATA = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean = oldItem.link == newItem.link
            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean = oldItem == newItem
        }
    }
}