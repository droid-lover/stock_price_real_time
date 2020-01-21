package com.vs.models

import com.google.gson.annotations.SerializedName
/**
 * Created By Sachin
 */
class StocksResult {

    @SerializedName("success")
    var success: Boolean? = null
    @SerializedName("data")
    var data: List<Datum>? = null

}