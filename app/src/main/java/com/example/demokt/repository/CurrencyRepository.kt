package com.example.demokt.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.demokt.activities.MainActivity
import com.example.demokt.data.CurrencyDao
import com.example.demokt.data.CurrencyService
import com.example.demokt.data.RetrofitHelper
import com.example.demokt.model.Currency
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CurrencyRepository(mainActivity: MainActivity, private val currencyDao: CurrencyDao) {

    var _mainActivity = mainActivity
    lateinit var listOfcurrency: HashMap<String, String>
    var _listOfcurrency: ArrayList<Currency> = arrayListOf()
    var readAllData = MutableLiveData<List<Currency>>()

/******************************this function returns true if the room is empty*****************************/
    fun isEmptyDB(): Boolean {
        var ifEmptyVar = false
        //Log.println(Log.ASSERT, "============>sizeeee",readAllData.value?.size.toString())
        readAllData.observe(_mainActivity, Observer {

            if (it.isEmpty()) {
                ifEmptyVar = true
            }
        })
        return ifEmptyVar

    }

    /******************************this function gets data from API(W/ connection)*****************************/
    fun getData(): LiveData<List<Currency>> {


        try {
            val service = RetrofitHelper.getInstance().create(CurrencyService::class.java)
            MainScope().launch {


                var response = service.getCurrency()
                //Log.println(Log.ASSERT, "============>Response", response.body()?.data.toString())

                listOfcurrency = response.body()?.data as HashMap<String, String>
                //Log.println(Log.ASSERT, "============>", response.body().toString())
                for ((k, v) in listOfcurrency) {
                    var currency = Currency(k, v)
                    currency.id = k
                    currency.value = v
                    listOfcurrency.put(k, v)
                    _listOfcurrency.add(currency)
                    currencyDao.addCurrency(currency)

                }

                _mainActivity.showInView(_listOfcurrency)
                _mainActivity.showRates(_listOfcurrency)

            }

        } catch (e: Exception) {

        }


        return readAllData

    }

 /******************************this function gets data from Room*****************************/
    fun getDataFromDB() {
     /************************observe update the data of the room***********************/
        currencyDao.readAllData().observe(_mainActivity, Observer {
            listOfcurrency = HashMap<String, String>()
            for (item in it) {
                var currency = Currency(item.id, item.value)

                listOfcurrency.put(item.id, item.value.toString())
                _listOfcurrency.add(currency)

            }
            if (_listOfcurrency.size == 0) {
                _mainActivity.showAlertIfNoConnectionNoDb()
                //Log.println(Log.ASSERT, "============>sizeeee", _listOfcurrency.size.toString())
            } else {

                _mainActivity.showInView(_listOfcurrency)
                _mainActivity.showRates(_listOfcurrency)
            }


        })
    }

    fun convert(base: Double, _base_convert: String) {
        //Log.println(Log.ASSERT, "=====>Hashmap", listOfcurrency.size.toString())

        var base_convert = 0.0
        var temp = 0.0
        var temp_convert = 0.0
        base_convert = (listOfcurrency.get(_base_convert)).toString().toDouble()

        for (rate in _listOfcurrency) {
            //for (rate in listOfcurrency){
            temp = listOfcurrency.get(rate.id).toString().toDouble()
            temp_convert = temp / base_convert


            rate.value = (temp_convert * base).toString()

            //_mainActivity.showInView(listOfcurrency)
            _mainActivity.showInView(_listOfcurrency)
            //Log.println(Log.ASSERT,"=====>rate",rate.id.toString())
        }

    }

    suspend fun addCurrency(currency: Currency) {
        currencyDao.addCurrency(currency)
    }


}