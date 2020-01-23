package com.vs.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vs.app.StockApp
import com.vs.db.StockDatabase
import com.vs.models.Datum
import com.vs.models.StocksResult
import com.vs.networking.ApiClient
import com.vs.utils.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created By Sachin
 */
class StocksRepo : Repo() {

    private val _stockResult = MutableLiveData<Result<StocksResult>>()
    val stockResult: LiveData<Result<StocksResult>> = _stockResult

    var stockName = MutableLiveData<String>()

    private val _stockResultFromDb = MutableLiveData<Result<List<Datum>>>()
    val stockResultFromDb: LiveData<Result<List<Datum>>> = _stockResultFromDb

    fun getStockResult(context: Context, queryValue: String) {
        disposables.add(
            ApiClient.client.getStockResult(queryValue)
                .map { sr ->
                    sr.data?.also {
                        for (item in it) {
                            item.timeStamp = Date().time
                        }
                    }
                    sr
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableSingleObserver<StocksResult>() {
                    override fun onSuccess(data: StocksResult) {
                        _stockResult.postValue(Result.Success(data))
                        _showProgressBar.postValue(false)
                        data.data?.also { saveStocksInDatabase(context, it) }
                    }

                    override fun onError(throwable: Throwable) {
                        _stockResult.postValue(Result.Failure(throwable))
                        _showProgressBar.postValue(false)
                    }
                })
        )
    }

    private fun saveStocksInDatabase(context: Context, data: List<Datum>) {
        disposables.add(Observable.fromCallable {
            StockDatabase.getDatabase(context).stockDao().insert(data)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //                    Utils.showToastMessage("Data Saved")
                Log.d("ComingHere", "inside_saveStocksInDatabase=Data Saved")
            })
    }

    fun getStocksFromDatabase(context: Context, sid: String) {
        disposables.add(Observable.fromCallable {
            StockDatabase.getDatabase(context).stockDao().getAllStocks(sid)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _stockResultFromDb.postValue(Result.Success(it))
                Log.d("ComingHere", "inside_saveStocksInDatabase=Data Saved")
            })
    }

}
