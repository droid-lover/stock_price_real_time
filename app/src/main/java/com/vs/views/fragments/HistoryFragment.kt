package com.vs.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vs.R
import com.vs.models.Datum
import com.vs.utils.Result
import com.vs.utils.Utils
import com.vs.viewmodels.StocksViewModel


/**
 * Created By Sachin
 */
class HistoryFragment : Fragment() {

    private val stocksViewModel by lazy { ViewModelProviders.of(this).get(StocksViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            //            noteData = it.getSerializable(C.NOTE) as Note
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stocksViewModel.stockResultFromDb.observe(this, Observer {
            when (it) {
                is Result.Success<List<Datum>> -> {
                    it.data?.let {
                        Log.d("ComingHere", "inside_stockResultFromDb_success ${it.size}")
                    }
                }
                is Result.Failure -> Utils.showToastMessage(it.throwable.localizedMessage)
            }
        })

        activity?.also { stocksViewModel.getDataForSpecificStock(it, "TCS") }
    }

//    private fun showNotesDetails(note: Note) {
//        tvNotesDetailsValue.text = "Title: "+note.title +"\n\n"+ "Description: "+note.description +"\n\n"+ "Timestamp: "+note.time
//    }
}
