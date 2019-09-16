package com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getz.setthegoal.R
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_suggestion.view.*

class WordsRV : BaseAdapter<Word, WordsRV.VH>() {

    lateinit var onClick: (position: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false))

    override fun getItemCount() = godList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val word = godList[position]
        holder.view.chipsa.text = word.originalWord
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.chipsa.setSingleClickListener { onClick.invoke(adapterPosition) }
        }
    }
}