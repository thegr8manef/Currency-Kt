package com.example.demokt.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demokt.R
import com.example.demokt.strings.Strings.Bar_Title_About
import com.example.demokt.strings.Strings.Url_Fb
import kotlinx.android.synthetic.main.activity_about.*
import java.util.*


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = Bar_Title_About
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        mark_btn.setOnClickListener(View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Url_Fb))
            startActivity(browserIntent)
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}