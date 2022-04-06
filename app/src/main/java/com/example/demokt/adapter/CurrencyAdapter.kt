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
import java.util.*

class CurrencyAdapter(
    val context: Context,
    private val listCurrency: ArrayList<Currency>?
) : RecyclerView.Adapter
<CurrencyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindView(listCurrency!![position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(data: Currency) {


            //  Log.e("Main","-------------------------"+list.get(position));

            if ((data.id != "BTN") && (data.id != "DOP") && (data.id != "XAF") && (data.id != "XCD") && (data.id != "XDR") && (data.id != "XOF") && (data.id != "XPF")) {
                val id = context.resources.getIdentifier(
                    data.id.lowercase(Locale.ROOT).substring(0, 2),
                    "drawable",
                    context.packageName
                )
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