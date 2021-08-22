package com.greenmug.newspressplay.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.adapters.BannerAdapter
import com.greenmug.newspressplay.databinding.ChannelActivityBinding
import com.greenmug.newspressplay.viewModels.TrendViewModel
import com.greenmug.newspressplay.listeners.PlayerListener
import com.greenmug.newspressplay.listeners.SaveLaterListener
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.player.PlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
    Activity shows of List of videos for a particular selected channel.
 */
@AndroidEntryPoint
class ChannelsActivity : AppCompatActivity(), PlayerListener, SaveLaterListener {

    private var viewModel: TrendViewModel? = null
    private var activityMainBinding: ChannelActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.channel_activity)
        var channel = intent.getStringExtra("channel")
        donInitialization(channel!!)
    }

    fun donInitialization(channel: String) {
        viewModel = ViewModelProvider(this).get(TrendViewModel::class.java)
        viewModel?.list?.observe(this,{
            var channels = activityMainBinding?.tvShowsRecyclerView
            channels?.adapter = BannerAdapter(it, this, this, this);
            channels?.adapter?.notifyDataSetChanged()
        })
        viewModel?.getContent(channel)
    }
    override fun onPlayer(url: String) {
        val intent = Intent(this, PlayerActivity::class.java).apply {
            putExtra("videoUrl", url)
        }
        startActivity(intent)
    }

    override fun bookMark(news: Favourites) {
        viewModel?.favouriteShowDatabase?.tvFacouritesDao()?.addToWatchList(news)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                Toast.makeText(
                    this,
                    "Added to Watch Later in Bookmark Section",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    @SuppressLint("CheckResult")
    override fun favourite(news: Favourites) {
        viewModel?.favouriteShowDatabase?.tvFacouritesDao()?.addToWatchList(news)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                Toast.makeText(
                    this,
                    "Added to Watch Later in Favorites Section ",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }




}