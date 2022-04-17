package com.example.demokt.data.remote

import com.example.demokt.R
import com.example.demokt.model.Currency
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyService {
    @GET("latest?apikey=bglhdrskIVmmxSy6wsQVns2SvQKSfFkMCKisbgUK")
    //suspend fun getCurrency() : Response<HashMap<Currency,Currency>>
    suspend fun getCurrency(): Response<Currency>

}