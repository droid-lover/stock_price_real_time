package com.vs.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vs.R
import com.vs.models.Datum
import com.vs.utils.C
import com.vs.utils.Result
import com.vs.utils.Utils
import com.vs.viewmodels.StocksViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import lecho.lib.hellocharts.model.*
import kotlin.math.absoluteValue


/**
 * Created By Sachin
 */
class HistoryFragment : Fragment() {

    private var stockData: Datum? = null

    private val stocksViewModel by lazy {
        ViewModelProviders.of(this).get(StocksViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            stockData = it.getSerializable(C.STOCK_DATA) as Datum
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stocksViewModel.stockResultFromDb.observe(this, Observer {
            when (it) {
                is Result.Success<List<Datum>> -> {
                    it.data?.let { data ->
                        setStockData(data)
//                        Log.d("ComingHere", "inside_stockResultFromDb_success ${data.size}")
//                        for(item in data){
//                            Log.d("ComingHere", "inside_stockResultFromDb_successLoop ${item.timeStamp}")
//                        }
                    }
                }
                is Result.Failure -> Utils.showToastMessage(it.throwable.localizedMessage)
            }
        })

        stockData?.also { getStockData(it) }
    }

    private fun getStockData(stock: Datum) {
        activity?.also { stocksViewModel.getDataForSpecificStock(it, stock.sid ?: "") }
    }

    private fun setStockData(it: List<Datum>) {
        val latestData = it[it.size - 1]
        tvStockLatestPrice.text = latestData.price.toString()
        val change = latestData.change.absoluteValue
        val percentage = Utils.priceFormatter(latestData.change / latestData.price)
        activity?.also {
            if (latestData.change > 0) {
                tvStockOtherDetails.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_up,
                    0,
                    0,
                    0
                )
                tvStockOtherDetails.setTextColor(ContextCompat.getColor(it, R.color.green))
                tvStockOtherDetails.text = "$change (+$percentage%)"
            } else {
                tvStockOtherDetails.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_down,
                    0,
                    0,
                    0
                )
                tvStockOtherDetails.setTextColor(ContextCompat.getColor(it, R.color.red))
                tvStockOtherDetails.text = "$change (-$percentage%)"
            }
        }


        setChartData(it)
    }

    private fun setChartData(it: List<Datum>) {
        val values = ArrayList<PointValue>()
        for (item in it) {
            values.add(PointValue(item.timeStamp.toFloat(), item.price.absoluteValue.toFloat()))

        }

        val line = Line(values).setColor(Color.BLUE).setCubic(true).setFilled(true)
        val lines = ArrayList<Line>()
        lines.add(line)

        val data = LineChartData()
        data.setLines(lines)



//        val axisValues = ArrayList<AxisValue>()
//        axisValues.add(0, AxisValue(0f).setLabel("0"))
//        axisValues.add(1, AxisValue(1f).setLabel("1"))
//        axisValues.add(2, AxisValue(2f).setLabel("2"))
//        axisValues.add(3, AxisValue(3f).setLabel("3"))
//
//
//        val yaxisValues = ArrayList<AxisValue>()
//        yaxisValues.add(0, AxisValue(0f).setLabel("0"))
//        yaxisValues.add(1, AxisValue(1f).setLabel("1"))
//        yaxisValues.add(2, AxisValue(2f).setLabel("2"))
//        yaxisValues.add(3, AxisValue(3f).setLabel("3"))
//        yaxisValues.add(4, AxisValue(4f).setLabel("4"))
//
//
//        val axis = Axis()
//        axis.setValues(axisValues)
//        axis.setTextSize(16);
//        axis.setTextColor(Color.parseColor("#03A9F4"));
//        data.setAxisXBottom(axis);
//
//
//
//        val yaxis = Axis()
//        yaxis.setValues(yaxisValues)
//        yaxis.setName("Sales in millions");
//        yaxis.setTextColor(Color.parseColor("#03A9F4"));
//        yaxis.setTextSize(16);
//        data.setAxisYLeft(yaxis);

        chart.setLineChartData(data)

//
//        val values = ArrayList<PointValue>()
//        values.add(PointValue(0f, 2f))
//        values.add(PointValue(1f, 4f))
//        values.add(PointValue(2f, 6f))
//        values.add(PointValue(3f, 8f))
//
//        val line = Line(values).setColor(Color.BLUE).setCubic(true).setFilled(true)
//        val lines = ArrayList<Line>()
//        lines.add(line)
//
//        val data = LineChartData()
//        data.setLines(lines)
//
//
//
//        val axisValues = ArrayList<AxisValue>()
//        axisValues.add(0, AxisValue(0f).setLabel("0"))
//        axisValues.add(1, AxisValue(1f).setLabel("1"))
//        axisValues.add(2, AxisValue(2f).setLabel("2"))
//        axisValues.add(3, AxisValue(3f).setLabel("3"))
//
//
//        val yaxisValues = ArrayList<AxisValue>()
//        yaxisValues.add(0, AxisValue(0f).setLabel("0"))
//        yaxisValues.add(1, AxisValue(1f).setLabel("1"))
//        yaxisValues.add(2, AxisValue(2f).setLabel("2"))
//        yaxisValues.add(3, AxisValue(3f).setLabel("3"))
//        yaxisValues.add(4, AxisValue(4f).setLabel("4"))
//
//
//        val axis = Axis()
//        axis.setValues(axisValues)
//        axis.setTextSize(16);
//        axis.setTextColor(Color.parseColor("#03A9F4"));
//        data.setAxisXBottom(axis);
//
//
//
//        val yaxis = Axis()
//        yaxis.setValues(yaxisValues)
//        yaxis.setName("Sales in millions");
//        yaxis.setTextColor(Color.parseColor("#03A9F4"));
//        yaxis.setTextSize(16);
//        data.setAxisYLeft(yaxis);
//
//        chart.setLineChartData(data)
    }

}
