package com.greenmug.newspressplay.player

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.greenmug.newspressplay.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util


/*
Player Activity plays different kinds of videos HLS, Smooth Streaming, Dash, Other like mp4
 */
class PlayerActivity : AppCompatActivity() {
    private val TAG = PlayerActivity::class.java.simpleName
    var playerView: PlayerView? = null
    private var exoPlayer: SimpleExoPlayer? = null
    private var mIsLongPress = false
    lateinit var progressBar: ProgressBar
    private var videoUrl:String?=null
    var duration:Int=0
    var currentTime:Long=0

    val handlerTask: Runnable = object : Runnable {
        @RequiresApi(api = Build.VERSION_CODES.N)
        override fun run() {
            if(exoPlayer!=null) {
                currentTime = exoPlayer!!.getCurrentPosition() / 1000
                if (currentTime > duration ) {
                stopTask()
                    pausePlayer()
                    releasePlayer()
                } else {
                     Handler().postDelayed(this, 1000)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        progressBar=findViewById(R.id.playerProgress)
        playerView = findViewById(R.id.playerView)
        videoUrl = intent.getStringExtra("videoUrl")
        Log.d("player","Url"+videoUrl)
        duration = intent.getIntExtra("showTime",0)
    }

    override fun onResume() {
        super.onResume()
        initializePlayer()
        if (duration>0) {
            handlerTask.run()
        }
    }
    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        var mediaSource: MediaSource
        var dataSourceFactory: DataSource.Factory
        val uriOfContentUrl = Uri.parse(videoUrl)
        val mediaSourceType: Int = Util.inferContentType(uriOfContentUrl)
        // Create a data source factory.
        // Create a media source
        // Create a player instance.
        // Prepare the player.
        when (mediaSourceType) {
            C.TYPE_DASH -> {
                dataSourceFactory = DefaultHttpDataSourceFactory()
                mediaSource = DashMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uriOfContentUrl))
                exoPlayer = SimpleExoPlayer.Builder(this).build()
                exoPlayer!!.setMediaSource(mediaSource)
                exoPlayer!!.prepare()
                progressBar.visibility= View.GONE
            }
            C.TYPE_SS -> {
                dataSourceFactory = DefaultHttpDataSourceFactory()
                mediaSource = SsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uriOfContentUrl))
                exoPlayer = SimpleExoPlayer.Builder(this).build()
                exoPlayer!!.setMediaSource(mediaSource)
                exoPlayer!!.prepare()
                progressBar.visibility= View.GONE
            }
            C.TYPE_HLS -> {
                dataSourceFactory = DefaultHttpDataSourceFactory()
                val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uriOfContentUrl))
                exoPlayer = SimpleExoPlayer.Builder(this).build()
                exoPlayer!!.setMediaSource(hlsMediaSource)
                exoPlayer!!.prepare()
                progressBar.visibility= View.GONE
            }
            C.TYPE_OTHER -> {
                dataSourceFactory = DefaultHttpDataSourceFactory()
                mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))
                exoPlayer = SimpleExoPlayer.Builder(this).build()
                exoPlayer!!.setMediaSource(mediaSource)
                exoPlayer!!.prepare()
                progressBar.visibility= View.GONE
            }
            else -> {
                dataSourceFactory = DefaultHttpDataSourceFactory()
                mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uriOfContentUrl))
                exoPlayer = SimpleExoPlayer.Builder(this).build()
                exoPlayer!!.setMediaSource(mediaSource)
                exoPlayer!!.prepare()
                progressBar.visibility= View.GONE          }
        }

        playerView!!.player = exoPlayer
        this.exoPlayer?.addListener(object : Player.DefaultEventListener() {
            override fun onPlayerStateChanged(playWhenReady: Boolean,playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE -> {}
                    Player.STATE_BUFFERING -> {
                        playerView?.showController()
                        progressBar.visibility=View.VISIBLE
                    }
                    Player.STATE_READY -> {
                        playerView?.hideController()
                        progressBar.visibility=View.GONE
                    }
                    Player.STATE_ENDED -> {
                        stopTask()
                        pausePlayer()
                        releasePlayer()
                        finish()
                    }
                }
            }
        })
        exoPlayer!!.addListener(object : Player.EventListener {
            override fun onPlayerError(error: ExoPlaybackException) {
                Log.e("onPlayerError: ", error.toString())
            }

        })
        startPlayer()
    }

    private fun startPlayer() {
        if (exoPlayer != null) {
            exoPlayer!!.playWhenReady = true
            exoPlayer!!.playbackState
        }
    }

    private fun releasePlayer() {
        if (exoPlayer != null) {
            stopTask()
            exoPlayer!!.release()
            exoPlayer = null

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        var handled = false
        when (keyCode) {
            20 -> if (keyCode == 20) {
                playerView?.showController()
                playerView?.setUseController(true)
                playerView?.setControllerShowTimeoutMs(5000)
            }
            23 -> if (keyCode == 23) {
                playerView?.showController()
                playerView?.setUseController(true)
                playerView?.setControllerShowTimeoutMs(5000)
            }
            KeyEvent.KEYCODE_MEDIA_REWIND -> {
                if (event.getAction() === 1) {
                    mIsLongPress = true
                    startContinualRewind()
                }
                handled = true
            }
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                playerView?.showController()
                playerView?.setUseController(true)
                playerView?.setControllerShowTimeoutMs(5000)
                handled = true
            }
            KeyEvent.KEYCODE_MEDIA_FAST_FORWARD -> {
                if (event.getAction() === 1) {
                    mIsLongPress = true
                    startContinualFastForward()
                }
                handled = true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun startContinualFastForward() {
        playerView!!.showController()
        playerView!!.useController = true
        playerView!!.controllerShowTimeoutMs = 5000
        playerView!!.setFastForwardIncrementMs(30000)
    }

    private fun startContinualRewind() {
        playerView!!.showController()
        playerView!!.useController = true
        playerView!!.controllerShowTimeoutMs = 5000
        playerView!!.setRewindIncrementMs(30000)
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }
    fun stopTask() {
        Handler().removeCallbacks(handlerTask)
    }
    private fun pausePlayer() {
        if (exoPlayer != null) {
            exoPlayer!!.playWhenReady = false
            exoPlayer!!.playbackState
        }
    }

}