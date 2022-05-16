package com.example.demokt.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.demokt.strings.Strings.TABLE_NAME

/************************set the name of the room********************/
@Entity(tableName = TABLE_NAME)
data class Currency(

    /************************the String id is the primaryKey********************/
    @PrimaryKey(autoGenerate = false)
    var id: String,
    /************************the String value is the Column********************/
    @ColumnInfo
    var value: String? = null,

    ) {
    /************************the data value is must be ignored********************/
    @Ignore
    var data = HashMap<String, String>()


}




