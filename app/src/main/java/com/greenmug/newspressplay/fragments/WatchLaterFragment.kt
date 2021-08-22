package com.greenmug.newspressplay.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.adapters.WatchLaterAdapter
import com.greenmug.newspressplay.databinding.WatchLaterFragmentBinding
import com.greenmug.newspressplay.listeners.PlayerListener
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.player.PlayerActivity
import com.greenmug.newspressplay.viewModels.WatchLaterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchLaterFragment : Fragment(),PlayerListener {
    private var watchLaterFragmentBinding: WatchLaterFragmentBinding? = null
    companion object {
        fun newInstance() = WatchLaterFragment()
    }


    private lateinit var viewModel: WatchLaterViewModel
    private var watchListFavourites = ArrayList<Favourites>();
    private var watchListBookMarks = ArrayList<Favourites>();
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        watchLaterFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.watch_later_fragment, container, false)
        return watchLaterFragmentBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WatchLaterViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel?.favouriteShowDatabase?.tvFacouritesDao()?.getWatchList()?.observe(requireActivity(), Observer<List<Favourites>> {
            Log.d("Malathi","Malathi DB"+it.size)
            for(n in 0 until it.size) {
                if(it?.get(n)?.type == "F") {
                    watchListFavourites.add(it.get(n))
                }else {
                    watchListBookMarks.add(it.get(n))
                }
            }
            watchLaterFragmentBinding?.tvShowsRecyclerView?.adapter = WatchLaterAdapter(watchListFavourites,this)
            watchLaterFragmentBinding?.tvShowsRecyclerView2?.adapter = WatchLaterAdapter(watchListBookMarks,this)
        })

    }

    override fun onPlayer(url: String) {
        val intent = Intent(activity, PlayerActivity::class.java).apply {
            putExtra("videoUrl", url)
        }
        startActivity(intent)
    }

}