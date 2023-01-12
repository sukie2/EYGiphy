package com.sukitha.ey.eygiphy.presentation.favourite

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

class FavouriteGiphyListAdapter(
    val data: List<Giphy>,
    val onClick: (Giphy) -> Unit
) :
    RecyclerView.Adapter<FavouriteGiphyListAdapter.GiphyViewHolder>() {

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
            onClick(data[position])
        }
        Glide.with(holder.imageView.context)
            .asGif()
            .load(data[position].url)
            .placeholder(R.drawable.ic_baseline_gif_24)
            .override(250, 250)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)

        Glide.with(holder.favImageView.context)
            .asBitmap()
            .load(R.drawable.ic_baseline_favorite_24)
            .override(80, 80)
            .into(holder.favImageView)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}