package com.greenmug.newspressplay.fragments

import android.content.Intent
import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.activity.SearchActivity
import com.greenmug.newspressplay.adapters.BannerAdapter
import com.greenmug.newspressplay.databinding.ActivityAllVideosBinding

import com.greenmug.newspressplay.listeners.PlayerListener
import com.greenmug.newspressplay.listeners.SaveLaterListener
import com.greenmug.newspressplay.models.EdgeNetUrl
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.player.PlayerActivity
import com.greenmug.newspressplay.signup.SignInActivity
import com.greenmug.newspressplay.utilities.Constants
import com.greenmug.newspressplay.utilities.PreferenceManager
import com.greenmug.newspressplay.viewModels.TrendViewModel

import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendFragment() : Fragment(), PlayerListener, SaveLaterListener  {

    private var trendFragmentBinding: ActivityAllVideosBinding? = null
    private lateinit var viewModel: TrendViewModel
    private  var clicked = false;
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


        var preferenceManager = PreferenceManager(requireActivity())
        if(preferenceManager != null && preferenceManager!!.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            trendFragmentBinding?.logout?.visibility = VISIBLE
        }else {
            trendFragmentBinding?.logout?.visibility = GONE
        }

        trendFragmentBinding?.logout?.setOnClickListener({
            var preferenceManager = PreferenceManager(requireActivity())
            preferenceManager?.clearPreferences();
            val intent = Intent(activity, SignInActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent);
        })

        viewModel?.list?.observe(viewLifecycleOwner,{
            var channels = trendFragmentBinding?.tvShowsRecyclerView
            channels?.adapter = BannerAdapter(it, context!!, this, this);
            channels?.adapter?.notifyDataSetChanged()
        })
        viewModel?.getContent()
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