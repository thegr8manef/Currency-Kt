package com.example.demokt.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demokt.model.Currency

@Dao
interface CurrencyDao {
    /************************Insert data into model Currency and if data have changed from the API the same data will be changed also in the room********************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(currency: Currency)

    /************************The data Currency will be stored into list of type LiveData the name of function is readAllData()********************/

    @Query("SELECT * FROM currency_table ORDER BY  id ASC ")
    fun readAllData(): LiveData<List<Currency>>

}