package com.vs.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vs.repositories.StocksRepo

/**
 * Created By Sachin
 */
class StocksViewModel : ViewModel() {

    private val repo = StocksRepo()
    var showProgressBar = repo.showProgressBar

    val stockResult = repo.stockResult
    val stockResultFromDb = repo.stockResultFromDb

    override fun onCleared() {
        super.onCleared()
        repo.onCleared()
    }

    fun getStockResult(context: Context, queryValue: String) = repo.getStockResult(context, queryValue)

    fun getDataForSpecificStock(context: Context,sid: String)= repo.getStocksFromDatabase(context, sid)
}

