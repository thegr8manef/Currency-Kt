package com.example.demokt.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class Currency(

    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo
    var value: String? = null,

    ) {
    @Ignore
    var data = HashMap<String, String>()


}




