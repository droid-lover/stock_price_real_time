package com.vs.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vs.R
import com.vs.adapters.StocksAdapter
import com.vs.models.StocksResult
import com.vs.utils.ItemOffsetDecoration
import com.vs.utils.Utils
import com.vs.viewmodels.StocksViewModel
import io.reactivex.disposables.CompositeDisposable
import com.vs.utils.Result
import com.vs.utils.RxBus
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit

/**
 * Created By Sachin
 */
class HomeFragment : Fragment() {

    private val stocksViewModel by lazy { ViewModelProviders.of(this).get(StocksViewModel::class.java) }
    private var compositeDisposable = CompositeDisposable()
    private var stocksAdapter: StocksAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        setStocksList()
    }

    private fun setStocksList() {
        activity?.also {
            activity?.also { context ->
                stocksAdapter = StocksAdapter(context)
                rvStocks?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = stocksAdapter
                }
                if (rvStocks.itemDecorationCount == 0) {
                    val spacing = resources.getDimensionPixelOffset(R.dimen.default_spacing_small)
                    rvStocks.addItemDecoration(ItemOffsetDecoration(spacing))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable = CompositeDisposable()
    }

    private fun observeViewModelChanges() {
        stocksViewModel.stockResult.observe(this, Observer {
            when (it) {
                is Result.Success<StocksResult> -> updateData(it.data)
                is Result.Failure -> Utils.showToastMessage(it.throwable.localizedMessage)
            }
        })
        stocksViewModel.showProgressBar.observe(this, Observer
        { if (it) Utils.showProgressDialog(activity!!) else Utils.hideProgressDialog() })

        compositeDisposable.add(RxBus.playClicked.subscribe {
            if (it == true) startPolling() else stopPolling()
        })
        compositeDisposable.add(RxBus.historyClicked.subscribe {
            goToHistoryPage()
        })
    }

    private fun stopPolling() {
        compositeDisposable.dispose()
    }

    private fun goToHistoryPage() {

    }

    private fun startPolling() {
        compositeDisposable = CompositeDisposable()
        val queryValue = "TCS,RELI,HDBK,INFY,HLL,ITC"
        //execute in every 5000 ms ≈5secs≈
        compositeDisposable.add(Observable.interval(0, 5000, TimeUnit.MILLISECONDS)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    activity?.also { stocksViewModel.getStockResult(it, queryValue) }
                })
    }

    private fun updateData(stocksResult: StocksResult) {
        stocksResult.data?.also {
            tvNoData?.visibility = View.GONE
            rvStocks?.visibility = View.VISIBLE
            stocksAdapter?.updateData(it)
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }
}
