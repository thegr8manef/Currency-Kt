package com.example.demokt.data

import com.example.demokt.model.Currency
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyService {
    @GET("latest?apikey=cxqcXfaSNEyjou73nst6nPrMJTcup8nM5howOZ0p")
    //suspend fun getCurrency() : Response<HashMap<Currency,Currency>>
    suspend fun getCurrency(): Response<Currency>

}