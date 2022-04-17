package com.example.demokt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.demokt.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity() : AppCompatActivity() {
    private lateinit var alertDialog: android.app.AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splash.alpha = 0f
        splash.animate().setDuration(1500).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }

}