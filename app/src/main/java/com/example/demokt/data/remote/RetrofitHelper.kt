package com.example.demokt.data.remote

import com.example.demokt.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    @JvmStatic
    fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://currencyapi.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}