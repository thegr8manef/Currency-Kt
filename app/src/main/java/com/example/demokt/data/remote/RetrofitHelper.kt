package com.example.demokt.data.remote

import com.example.demokt.R
import com.example.demokt.strings.Strings.BASEURL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    @JvmStatic
    fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}