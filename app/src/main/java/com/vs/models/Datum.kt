package com.vs.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created By Sachin
 */
@Entity
data class Datum(

        @ColumnInfo
        @SerializedName("sid")
        var sid: String? = null,
        @ColumnInfo
        @SerializedName("price")
        var price: Double = 0.0,
        @ColumnInfo
        @SerializedName("close")
        var close: Double = 0.0,
        @ColumnInfo
        @SerializedName("change")
        var change: Double = 0.0,
        @ColumnInfo
        @SerializedName("high")
        var high: Double = 0.0,
        @ColumnInfo
        @SerializedName("low")
        var low: Double = 0.0,
        @ColumnInfo
        @SerializedName("volume")
        var volume: Double = 0.0,
        @ColumnInfo
        @SerializedName("date")
        var date: String? = null

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}