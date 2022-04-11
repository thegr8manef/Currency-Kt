package com.example.demokt.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.demokt.activities.MainActivity
import com.example.demokt.data.local.CurrencyDatabase
import com.example.demokt.model.Currency
import com.example.demokt.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(mainActivity: MainActivity) : AndroidViewModel(mainActivity.application) {
    private var currencyRepository: CurrencyRepository

    init {
        val currencyDao = CurrencyDatabase.getDatabase(mainActivity.application).currencyDao()
        currencyRepository = CurrencyRepository(mainActivity, currencyDao)

    }


    fun getData(): LiveData<List<Currency>> {
        return currencyRepository.getData()
    }

    fun convert(base: Double, base_convert: String) {
        currencyRepository.convert(base, base_convert)

    }

    fun addCurrency(currency: Currency) {

        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.addCurrency(currency)
        }
    }

    fun callFunDataBD() {
        currencyRepository.getDataFromDB()

    }

    fun callIfEmptyDB(): Boolean {

        return currencyRepository.isEmptyDB()

    }


}