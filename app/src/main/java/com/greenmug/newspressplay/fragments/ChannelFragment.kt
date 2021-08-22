package com.greenmug.newspressplay.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.activity.ChannelsActivity
import com.greenmug.newspressplay.adapters.ChannelAdapter
import com.greenmug.newspressplay.databinding.FavouriteFragmentBinding
import com.greenmug.newspressplay.listeners.ChannelListener
import com.greenmug.newspressplay.viewModels.ChannelViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
    Channel Fragment
 */
@AndroidEntryPoint
class ChannelFragment() : Fragment(), ChannelListener  {

    private var favouriteFragmentBinding: FavouriteFragmentBinding? = null

    private lateinit var viewModel: ChannelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favouriteFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.favourite_fragment, container, false)
        return favouriteFragmentBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChannelViewModel::class.java)
        viewModel?.channels?.observe(viewLifecycleOwner,{
            var channels = favouriteFragmentBinding?.tvShowsRecyclerView
            channels?.adapter = ChannelAdapter(it, requireContext(), this);
            channels?.adapter?.notifyDataSetChanged()
        })
        viewModel?.getChannels()
    }

    override fun openChannel(channel: String) {
            val intent = Intent(activity, ChannelsActivity::class.java).apply {
                putExtra("channel", channel)
            }
            startActivity(intent)
    }
}