package com.example.demokt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demokt.R
import com.example.demokt.model.Currency
import com.example.demokt.strings.Strings.DELETED_CURRENCY_1
import com.example.demokt.strings.Strings.DELETED_CURRENCY_2
import com.example.demokt.strings.Strings.DELETED_CURRENCY_3
import com.example.demokt.strings.Strings.DELETED_CURRENCY_4
import com.example.demokt.strings.Strings.DELETED_CURRENCY_5
import com.example.demokt.strings.Strings.DELETED_CURRENCY_6
import com.example.demokt.strings.Strings.DELETED_CURRENCY_7
import java.util.*

class CurrencyAdapter(
    val context: Context,
    private val listCurrency: ArrayList<Currency>?
) : RecyclerView.Adapter
<CurrencyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_currency, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindView(listCurrency!![position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(data: Currency) {


            //  Log.e("Main","-------------------------"+list.get(position));
                                            /************Delete countries without flags*************/
            if ((data.id != DELETED_CURRENCY_1) && (data.id != DELETED_CURRENCY_2) && (data.id != DELETED_CURRENCY_3) && (data.id != DELETED_CURRENCY_4) && (data.id != DELETED_CURRENCY_5) && (data.id != DELETED_CURRENCY_6) && (data.id !=DELETED_CURRENCY_7)) {
                                            /************Set the Flags by their first two letters of countries*************/
                val id = context.resources.getIdentifier(
                    data.id.lowercase(Locale.ROOT).substring(0, 2),
                    "drawable",
                    context.packageName
                )
                                             /************Send data to the layout*************/
                itemView.findViewById<TextView>(R.id.id).text = data.id
                itemView.findViewById<TextView>(R.id.value).text = data.value
                itemView.findViewById<ImageView>(R.id.img).setImageResource(id)

            }
        }
    }

    override fun getItemCount(): Int {
        return listCurrency!!.size
    }


}