package com.greenmug.newspressplay.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.activity.ChannelsActivity
import com.greenmug.newspressplay.databinding.ItemContainerChannelBinding
import com.greenmug.newspressplay.databinding.ItemContainerViewBinding
import com.greenmug.newspressplay.listeners.ChannelListener
import com.greenmug.newspressplay.listeners.PlayerListener
import com.greenmug.newspressplay.models.Channels
import com.greenmug.newspressplay.models.News
import com.greenmug.newspressplay.player.PlayerActivity

/*
    Adapter for all Channels
 */
class ChannelAdapter(private val news: List<Channels>, private val context : Context,  private val channelListener: ChannelListener) : RecyclerView.Adapter<ChannelAdapter.ChannelShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelShowViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ItemContainerChannelBinding>(
            inflater,
            R.layout.item_container_channel,
            parent,
            false
        )

        return ChannelShowViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    inner class ChannelShowViewHolder(private val binding: ItemContainerChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTvShow(channels: Channels) {
            binding.channels = channels
            binding.executePendingBindings()
            binding?.imagePoster?.setOnClickListener {
                channelListener?.openChannel(channels.channel)
            }
        }
    }

    override fun onBindViewHolder(holder: ChannelShowViewHolder, position: Int) {
        holder.bindTvShow(news[position])
    }

}