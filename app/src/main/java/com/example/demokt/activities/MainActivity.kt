package com.example.demokt.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demokt.R
import com.example.demokt.adapter.CurrencyAdapter
import com.example.demokt.model.Currency
import com.example.demokt.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var alertDialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                this.showAlertIfNoConnection()

            } else {
                //Log.println(Log.ASSERT,"=======//////////////////////////","4")
                               /************get data from room************/
                currencyVM.getData()
                                /************spinner************/
                this.loadingAlert()

            }
        } else {
                                /************If the room is empty with data************/
            //Log.println(Log.ASSERT,"=======//////////////////////////","5")

            if (!isNetworkConnected()) {
                //Log.println(Log.ASSERT,"=======//////////////////////////","6")

                currencyVM.callFunDataBD()
                this.showAlertIfNoConnection()

            } else {
                             /************get data from API************/
                currencyVM.getData()
                //Log.println(Log.ASSERT,"=======//////////////////////////","7")
            }


        }
                    /************If the user click the button of convert************/
        btn_convert.setOnClickListener {
            currencyVM.convert(base.text.toString().toDouble(), menu.text.toString())
            //Log.println(Log.ASSERT,"=======================>list",menu.text.toString())

        }
    }
                        /*************This function show the data in listView***********/
    fun showInView(arrayWithData: ArrayList<Currency>?) {
        val adapter = CurrencyAdapter(this, arrayWithData)
        recyclerViewer.layoutManager = LinearLayoutManager(this)
        recyclerViewer.adapter = adapter
        if (isNetworkConnected()) {
            alertDialog.dismiss()
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
    fun showAlertIfNoConnection() {
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
    }
                        /*************this is the function of alert of the connection and for the room***********/
    fun showAlertIfNoConnectionNoDb() {
        val builder = AlertDialog.Builder(this@MainActivity)
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
        builder.show()
    }
                         /*************this is the function to test if the device is connected or not***********/
    fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
                        /*************this is the function of the spinner***********/
    fun loadingAlert() {
        var dialogView = LayoutInflater.from(this).inflate(R.layout.loading_alert, null)
        val builder = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("")
            .setCancelable(true)

        alertDialog = builder.show()
    }


}