package com.getz.setthegoal.presentationpart.feature.creategoal.writegoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_suggestion.view.*

class SuggestionsRV(private val godList: ArrayList<Int>, val onClick: (position: Int) -> Unit) :
    BaseAdapter<Int, SuggestionsRV.VH>(godList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false))

    override fun getItemCount() = godList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val strResId = godList[position]
        holder.view.chipsa.text = holder.view.context.getString(strResId)
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.chipsa.setSingleClickListener { onClick.invoke(adapterPosition) }
        }
    }
}