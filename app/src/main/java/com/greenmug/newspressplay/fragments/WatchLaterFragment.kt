package com.greenmug.newspressplay.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.adapters.WatchLaterAdapter
import com.greenmug.newspressplay.databinding.WatchLaterFragmentBinding
import com.greenmug.newspressplay.listeners.PlayerListener
import com.greenmug.newspressplay.listeners.RemoveLaterListener
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.player.PlayerActivity
import com.greenmug.newspressplay.viewModels.WatchLaterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
Fragment for Watch Later
 */
@AndroidEntryPoint
class WatchLaterFragment : Fragment(),PlayerListener,RemoveLaterListener {
    private var watchLaterFragmentBinding: WatchLaterFragmentBinding? = null
    private  var clicked = false;
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
            for(n in 0 until it.size) {
                if(it?.get(n)?.type == "F") {
                    watchListFavourites.add(it.get(n))
                }else {
                    watchListBookMarks.add(it.get(n))
                }
            }
            watchLaterFragmentBinding?.tvShowsRecyclerView?.adapter = WatchLaterAdapter(watchListFavourites,this,this)
            watchLaterFragmentBinding?.tvShowsRecyclerView2?.adapter = WatchLaterAdapter(watchListBookMarks,this,this)
            watchLaterFragmentBinding?.tvShowsRecyclerView?.adapter?.notifyDataSetChanged()
            watchLaterFragmentBinding?.tvShowsRecyclerView2?.adapter?.notifyDataSetChanged()
        })

    }

    override fun onPlayer(url: String,content_id :String) {
        if(clicked == false) {
            clicked = true;
            CoroutineScope(Dispatchers.Main).launch {
                var s = viewModel?.edgeNetRepository?.getAllContentId(content_id);
                startPlayer(url, s.body()?.url!!)
                clicked  = false;
            }
        }
    }

    fun startPlayer(url: String,edgeNetUrl: String) {
        val intent = Intent(activity, PlayerActivity::class.java).apply {
            putExtra("videoUrl", url)
            putExtra("edgeNetUrl", edgeNetUrl)
        }
        startActivity(intent)
    }

    override fun remove(favourites: Favourites, type: String) {
        viewModel?.remove(favourites)
        if(type.equals("F")) {
            watchListFavourites.remove(favourites)
            watchLaterFragmentBinding?.tvShowsRecyclerView?.adapter?.notifyDataSetChanged()
        }else {
            watchListBookMarks.remove(favourites)
            watchLaterFragmentBinding?.tvShowsRecyclerView2?.adapter?.notifyDataSetChanged()
        }
        Toast.makeText(
            activity,
            "Removed Video from Watch Later Section",
            Toast.LENGTH_SHORT
        ).show()

    }

}