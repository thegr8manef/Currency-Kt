package com.example.demokt.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demokt.model.Currency

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(currency: Currency)

    @Query("SELECT * FROM currency_table ORDER BY  id ASC ")
    fun readAllData(): LiveData<List<Currency>>

}