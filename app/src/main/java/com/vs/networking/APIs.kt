package com.vs.networking

import com.vs.models.StocksResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sachin.
 */

interface APIs {

    //BASE_URL_MOVIES
    @GET("stocks/quotes")
    fun getStockResult(@Query("sids") sids: String): Single<StocksResult>


}