package com.greenmug.newspressplay.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.databinding.ItemContainerWatchlaterBinding
import com.greenmug.newspressplay.listeners.PlayerListener
import com.greenmug.newspressplay.models.Favourites

/*
    Adapter for all Watch Later Videos
 */
class WatchLaterAdapter(private val news: List<Favourites>,private val playerListener: PlayerListener) : RecyclerView.Adapter<WatchLaterAdapter.WatchLaterViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchLaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ItemContainerWatchlaterBinding>(
            inflater,
            R.layout.item_container_watchlater,
            parent,
            false
        )

        return WatchLaterViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    inner class WatchLaterViewHolder(private val binding: ItemContainerWatchlaterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTvShow(news: Favourites) {
            binding.favourites = news
            binding.executePendingBindings()
            binding?.imagePoster.setOnClickListener {
                playerListener.onPlayer(news.url)
            }
        }
    }

    override fun onBindViewHolder(holder: WatchLaterViewHolder, position: Int) {
        holder.bindTvShow(news[position])
    }


}