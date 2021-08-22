package com.greenmug.newspressplay.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.greenmug.newspressplay.R
import com.greenmug.newspressplay.databinding.ActivitySplashBinding
import android.content.Intent
import android.os.Handler
import com.greenmug.newspressplay.signup.SignInActivity


/*
    Splash Activity
 */
class SplashActivity : AppCompatActivity() {
    private var splashScreen : ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
            finish()
        }, 3000)
    }


}