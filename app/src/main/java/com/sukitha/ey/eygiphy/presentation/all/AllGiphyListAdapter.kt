package com.sukitha.ey.eygiphy.presentation.all

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sukitha.ey.eygiphy.R
import com.sukitha.ey.eygiphy.domain.data.Giphy

class AllGiphyListAdapter(
    val data: List<Giphy>,
    private val favourites: List<Giphy>,
    val onClick: (Giphy, Boolean) -> Unit
) :
    RecyclerView.Adapter<AllGiphyListAdapter.GiphyViewHolder>() {

    inner class GiphyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.titleText)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val favImageView: ImageView = itemView.findViewById(R.id.favImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_giphy_row, parent, false)
        return GiphyViewHolder(view)
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        holder.textView.text = data[position].title
        holder.favImageView.setOnClickListener {
            toggleFavourite(data[position])
        }
        Glide.with(holder.imageView.context)
            .asGif()
            .load(data[position].url)
            .placeholder(R.drawable.ic_baseline_gif_24)
            .override(250, 250)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)

        var favIcon = R.drawable.ic_baseline_favorite_border_24
        if (isFavourite(data[position])) {
            favIcon = R.drawable.ic_baseline_favorite_24
        }
        Glide.with(holder.favImageView.context)
            .asBitmap()
            .load(favIcon)
            .override(80, 80)
            .into(holder.favImageView)
    }

    private fun toggleFavourite(giphy: Giphy) {
        val items = favourites.filter { it.id == giphy.id }
        if (items.isNotEmpty()) {
            onClick(items[0], false)
        } else {
            onClick(giphy, true)
        }
    }

    private fun isFavourite(giphy: Giphy): Boolean {
        return favourites.filter { it.id == giphy.id }.size == 1
    }

    override fun getItemCount(): Int {
        return data.size
    }
}