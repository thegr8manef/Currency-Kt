package com.example.demokt.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demokt.R
import com.example.demokt.adapter.CurrencyAdapter
import com.example.demokt.model.Currency
import com.example.demokt.viewmodel.CurrencyViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var alertDialog: android.app.AlertDialog
    lateinit var textView1: TextView
    lateinit var textView: TextView
    lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Home"

        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
        setContentView(R.layout.activity_main)
        textView1 = findViewById<TextView>(R.id.try_again)
        imageView = findViewById<ImageView>(R.id.wifi_off)
        val selectedList = ArrayList<Int>()

        //Log.println(Log.ASSERT,"=======>database",CurrencyDatabase.getDatabase(this).toString())
        var currencyVM = CurrencyViewModel(this)

        //Log.println(Log.ASSERT,"=======//////////////////////////","1")
        /************If the room is full with data*************/
        if (!currencyVM.callIfEmptyDB()) {
            //Log.println(Log.ASSERT,"=======//////////////////////////","2")
            /************If the device isn't connected to network*************/
            if (!isNetworkConnected()) {
                //Log.println(Log.ASSERT,"=======//////////////////////////","3")
                /************call the data from room*************/
                currencyVM.callFunDataBD()
                /************Alert for the connection of the network************/
                //this.showAlertIfNoConnection()

            } else {
                //Log.println(Log.ASSERT,"=======//////////////////////////","4")
                /************get data from room************/
                currencyVM.getData()
                /************spinner************/
               //this.loadingAlert()

            }
        } else {
            /************If the room is empty************/
            //Log.println(Log.ASSERT,"=======//////////////////////////","5")

            if (!isNetworkConnected()) {
                //Log.println(Log.ASSERT,"=======//////////////////////////","6")

                currencyVM.callFunDataBD()
                //this.showAlertIfNoConnection()

            } else {
                /************get data from API************/
                currencyVM.getData()
                //Log.println(Log.ASSERT,"=======//////////////////////////","7")
            }


        }
        /************If the user click the button of convert************/
        btn_convert.setOnClickListener {
            try {
                currencyVM.convert(base.text.toString().toDouble(), menu.text.toString().uppercase())
                //Log.println(Log.ASSERT,"=======================>list",menu.text.toString())
                val mySnackbar = Snackbar.make(findViewById(R.id.main), R.string.SnackBarProcessing, Snackbar.LENGTH_SHORT)
                mySnackbar.setDuration(500)
                mySnackbar.show()

            }catch (e : NumberFormatException){
                val mySnackbar = Snackbar.make(findViewById(R.id.main), R.string.SnackBarVerifing, Snackbar.LENGTH_SHORT)
                mySnackbar.setDuration(2000)
                mySnackbar.show()
            }
            HideKeyboard()



        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_help ->{
                val intent = Intent(this,HelpActivity::class.java).apply {

                }
                startActivity(intent)
                return true
            }
            R.id.action_abt ->{
                val intent1 = Intent(this,AboutActivity::class.java).apply {

                }
                startActivity(intent1)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*************This function show the data in listView***********/
    fun showInView(arrayWithData: ArrayList<Currency>?) {
        val adapter = CurrencyAdapter(this, arrayWithData)
        recyclerViewer.layoutManager = LinearLayoutManager(this)
        recyclerViewer.adapter = adapter
        if (isNetworkConnected()) {
            //alertDialog.dismiss()
        }


    }

    /*************This function show the data in list group***********/
    fun showRates(list: ArrayList<Currency>) {
        var _list = ArrayList<String>()

        for (item in list) {
            _list.add(item.id.toString())

        }
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, _list)
        menu.setAdapter(adapter)

    }
    /*************this is the function of alert of the connection***********/
/*    fun showAlertIfNoConnection() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Info")
        builder.setMessage("You are not Connected but you can continue .")
        builder.setCancelable(true)
        builder.setPositiveButton("Try again") { dialogInterface, i ->
            finish()
            startActivity(intent)

        }
        builder.setNegativeButton("OK") { dialogInterface, i ->
            onStart()
            startActivity(intent)
        }
        builder.show()
    }*/
    /*************this is the function of alert of the connection and for the room***********/
    fun showAlertIfNoConnectionNoDb() {
        imageView.setImageResource(R.mipmap.nowifi)
        textView1.text = Html.fromHtml("<u>Try Again</u>")
        textView1.setOnClickListener {
            finish()
            startActivity(intent)
        }
/*        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Error")
        builder.setMessage("Check your internet connection and try again.")
        builder.setCancelable(true)
        builder.setPositiveButton("Try again") { dialogInterface, i ->
            finish()
            startActivity(intent)

        }
        builder.setNegativeButton("Close the App") { dialogInterface, i ->
            finish()
        }
        builder.show()*/
    }

    /*************this is the function to test if the device is connected or not***********/
    fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    /*************this is the function of the spinner***********/
/*    fun loadingAlert() {
        var dialogView = LayoutInflater.from(this).inflate(R.layout.loading_alert, null)
        val builder = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("")
            .setCancelable(true)
        alertDialog = builder.show()
    }*/
    fun HideKeyboard() : Boolean{
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        return true
    }
}