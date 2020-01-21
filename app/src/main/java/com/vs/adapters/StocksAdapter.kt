package com.vs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vs.R
import com.vs.models.Datum

import kotlinx.android.synthetic.main.layout_note_item.view.*
import java.util.ArrayList


/**
 *  Created by Sachin
 */
class StocksAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<StocksAdapter.ViewHolder>() {
    private var data: List<Datum> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindToView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(data: List<Datum>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val tvNoteTitle = itemView.tvNoteTitle!!
        private val tvNoteDesc = itemView.tvNoteDesc!!
        private val cvNoteItemContainer = itemView.cvNoteItemContainer!!

        fun bindToView(data: Datum) {
            tvNoteTitle.text = data.sid
            tvNoteDesc.text = data.price.toString()
        }
    }

//    private fun goToNoteDetailsScreen(note: Note) {
//        val noteDetailsFragment = NoteDetailsFragment()
//        val bundle = Bundle()
//        bundle.putSerializable(C.NOTE, note)
//        noteDetailsFragment.arguments = bundle
//
//        (context as HomeActivity).also {
//            it.supportFragmentManager.beginTransaction()
//                .add(R.id.rlContainer, noteDetailsFragment, C.NOTE_DETAILS).addToBackStack("NoteDetailsFragment")
//                .commitAllowingStateLoss()
//        }
//    }
}
