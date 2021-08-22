package com.greenmug.newspressplay.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.databinding.ActivityHomeBinding
import com.greenmug.newspressplay.fragments.ChannelFragment
import com.greenmug.newspressplay.fragments.TrendFragment
import com.greenmug.newspressplay.fragments.WatchLaterFragment

import dagger.hilt.android.AndroidEntryPoint

/*
    Home Activity which loads Fragments associated with Bottom Navigation
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity(){

    private var activityHomeBinding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       activityHomeBinding =  DataBindingUtil.setContentView(this, R.layout.activity_home)
        activityHomeBinding?.bottomNavigation?.setOnItemSelectedListener  {menuItem ->

            when(menuItem.itemId) {
                R.id.trending -> {
                    loadFragment(TrendFragment())
                    true;
                    // Respond to navigation item 1 reselection
                }
                R.id.favourite -> {
                    loadFragment(ChannelFragment())
                    true;
                    // Respond to navigation item 2 reselection
                }
                R.id.later -> {
                    loadFragment(WatchLaterFragment())
                    true;
                    // Respond to navigation item 2 reselection
                }
                else -> {
                    loadFragment(TrendFragment())
                    true;
                }
            }
        }
        loadFragment(TrendFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit()

        }
    }



}