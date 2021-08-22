package com.greenmug.newspressplay.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.adapters.BannerAdapter
import com.greenmug.newspressplay.databinding.ActivitySearchBinding
import com.greenmug.newspressplay.listeners.PlayerListener
import com.greenmug.newspressplay.listeners.SaveLaterListener
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.player.PlayerActivity

import com.greenmug.newspressplay.viewModels.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

/*
    Activity shows of List of Videos Search by the Keyword
 */
class SearchActivity : AppCompatActivity(), PlayerListener, SaveLaterListener{

    private var activitySearchBinding: ActivitySearchBinding? = null
    private var viewModel: SearchViewModel? = null
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        doInitialization()
    }

    private fun doInitialization() {
        activitySearchBinding?.imageBack?.setOnClickListener { onBackPressed() }
        activitySearchBinding?.tvShowsRecyclerView?.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel?.list?.observe(this,{
            var channels = activitySearchBinding?.tvShowsRecyclerView
            channels?.adapter = BannerAdapter(it, applicationContext!!, this, this);
            channels?.adapter?.notifyDataSetChanged()
        })

        activitySearchBinding?.inputSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (timer != null)
                    timer?.cancel()
            }
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                viewModel?.getContent(s.toString())
                            }
                        }
                    }, 800)
                } else {
                   viewModel?.list?.postValue(ArrayList())
                }
            }
        })
        activitySearchBinding?.inputSearch?.requestFocus()
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