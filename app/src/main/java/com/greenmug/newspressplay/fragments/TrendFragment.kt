package com.greenmug.newspressplay.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.activity.SearchActivity
import com.greenmug.newspressplay.adapters.BannerAdapter
import com.greenmug.newspressplay.databinding.ActivityAllVideosBinding

import com.greenmug.newspressplay.listeners.PlayerListener
import com.greenmug.newspressplay.listeners.SaveLaterListener
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.player.PlayerActivity
import com.greenmug.newspressplay.viewModels.TrendViewModel

import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class TrendFragment() : Fragment(), PlayerListener, SaveLaterListener  {

    private var trendFragmentBinding: ActivityAllVideosBinding? = null

    private lateinit var viewModel: TrendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trendFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.activity_all_videos, container, false)
        return trendFragmentBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrendViewModel::class.java)
        trendFragmentBinding?.imageSearch?.setOnClickListener( {
            val intent = Intent(activity, SearchActivity::class.java).apply {
            }
            startActivity(intent)
        })
        viewModel?.list?.observe(viewLifecycleOwner,{
            var channels = trendFragmentBinding?.tvShowsRecyclerView
            channels?.adapter = BannerAdapter(it, context!!, this, this);
            channels?.adapter?.notifyDataSetChanged()
        })
        viewModel?.getContent()
    }

    override fun onPlayer(url: String) {
        val intent = Intent(activity, PlayerActivity::class.java).apply {
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
                    activity,
                    "Added to Watch Later in Bookmark Section",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun favourite(news: Favourites) {
        viewModel?.favouriteShowDatabase?.tvFacouritesDao()?.addToWatchList(news)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                Toast.makeText(
                    activity,
                    "Added to Watch Later in Favorites Section ",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}