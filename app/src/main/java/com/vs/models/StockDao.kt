package com.vs.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface StockDao {

    @Insert
    fun insert(data: List<Datum>)

    @Query("Select * from Datum WHERE `sid` =:stockName  ")
    fun getAllStocks(stockName: String): List<Datum>
}