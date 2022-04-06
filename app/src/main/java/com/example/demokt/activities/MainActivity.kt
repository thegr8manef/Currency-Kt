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

        if (!currencyVM.callIfEmptyDB()) {
            //Log.println(Log.ASSERT,"=======//////////////////////////","2")

            if (!isNetworkConnected()) {
                //Log.println(Log.ASSERT,"=======//////////////////////////","3")
                currencyVM.callFunDataBD()
                this.showAlertIfNoConnection()

            } else {
                //Log.println(Log.ASSERT,"=======//////////////////////////","4")
                currencyVM.getData()
                this.loadingAlert()

            }
        } else {
            //Log.println(Log.ASSERT,"=======//////////////////////////","5")

            if (!isNetworkConnected()) {
                //Log.println(Log.ASSERT,"=======//////////////////////////","6")

                currencyVM.callFunDataBD()
                this.showAlertIfNoConnection()

            } else {

                currencyVM.getData()
                //Log.println(Log.ASSERT,"=======//////////////////////////","7")
            }


        }
        btn_convert.setOnClickListener {
            currencyVM.convert(base.text.toString().toDouble(), menu.text.toString())
            //Log.println(Log.ASSERT,"=======================>list",menu.text.toString())

        }
    }

    fun showInView(arrayWithData: ArrayList<Currency>?) {
        val adapter = CurrencyAdapter(this, arrayWithData)
        recyclerViewer.layoutManager = LinearLayoutManager(this)
        recyclerViewer.adapter = adapter
        if (isNetworkConnected()) {
            alertDialog.dismiss()
        }

    }

    fun showRates(list: ArrayList<Currency>) {
        var _list = ArrayList<String>()

        for (item in list) {
            _list.add(item.id.toString())

        }
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, _list)
        menu.setAdapter(adapter)

    }

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

    fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun loadingAlert() {
        var dialogView = LayoutInflater.from(this).inflate(R.layout.loading_alert, null)
        val builder = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("")
            .setCancelable(true)

        alertDialog = builder.show()
    }


}