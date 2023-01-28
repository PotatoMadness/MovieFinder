package com.example.moviefinder.ui

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviefinder.R
import com.example.moviefinder.databinding.ListMovieItemBinding
import com.example.moviefinder.model.MovieItem

class MovieListAdapter(
    private val onFavoriteClicked: (item: MovieItem) -> Unit,
    private val onItemClicked: ((url: String) -> Unit)? = null
) : PagingDataAdapter<MovieItem, MovieListAdapter.ViewHolder>(DIFF_ITEM_STATION_DATA) {

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            getItem(position)?.let { item ->
                root.setOnClickListener {
                    onItemClicked?.invoke(item.link)
                }
                val context = holder.itemView.context
                Glide.with(context).load(item.image)
                    .into(imgMovieItem)
                tvTitle.text = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString()
                tvDirector.text = Html.fromHtml(String.format(context.getString(R.string.movie_director), item.director), Html.FROM_HTML_MODE_LEGACY).toString()
                tvActors.text = Html.fromHtml(String.format(context.getString(R.string.movie_actors), item.actor), Html.FROM_HTML_MODE_LEGACY).toString()
                tvRate.text = Html.fromHtml(String.format(context.getString(R.string.movie_rate), item.userRating), Html.FROM_HTML_MODE_LEGACY).toString()

                val imgRes = if(item.isFavorite) R.drawable.icon_star_fill_lg else R.drawable.icon_star_lg
                imgFav.setImageDrawable(context.getDrawable(imgRes))

                imgFav.setOnClickListener{
                    onFavoriteClicked(item)
                }
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