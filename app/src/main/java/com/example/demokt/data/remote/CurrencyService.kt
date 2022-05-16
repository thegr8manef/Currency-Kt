package com.example.demokt.data.remote

import com.example.demokt.R
import com.example.demokt.model.Currency
import com.example.demokt.strings.Strings.APIKEY
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyService {
    @GET(APIKEY)
    //suspend fun getCurrency() : Response<HashMap<Currency,Currency>>
    suspend fun getCurrency(): Response<Currency>

}