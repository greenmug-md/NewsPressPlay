package com.greenmug.newspressplay.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.greenmug.newspressplay.R

import com.greenmug.newspressplay.databinding.ItemContainerViewBinding
import com.greenmug.newspressplay.listeners.*
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.models.News


/*
    Adapter for all Videos
 */
class BannerAdapter(private val news: List<News>, private val context : Context, private val playerListener: PlayerListener, private  val saveLaterListener: SaveLaterListener) : RecyclerView.Adapter<BannerAdapter.BannerShowViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerShowViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ItemContainerViewBinding>(
            inflater,
            R.layout.item_container_view,
            parent,
            false
        )
        return BannerShowViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    inner class BannerShowViewHolder(private val binding: ItemContainerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTvShow(news: News) {
            binding.news = news
            binding.executePendingBindings()
            binding?.imagePoster.setOnClickListener {
                playerListener.onPlayer(news.url, news.content_id)
            }

            binding?.favourite?.setOnClickListener {
                var favourites = Favourites(name=news.name,image=news.image,url = news.url, content_id = news.content_id,
                type="F",content = news.content)
                saveLaterListener?.favourite(favourites)
            }

            binding?.bookmark?.setOnClickListener {
                var bookmarks = Favourites(name=news.name,image=news.image,url = news.url, content_id = news.content_id,
                    type="B",content = news.content)
                saveLaterListener?.bookMark(bookmarks)
            }
        }
    }

    override fun onBindViewHolder(holder: BannerShowViewHolder, position: Int) {
        holder.bindTvShow(news[position])
    }


}