package com.getz.setthegoal.presentationpart.feature.creategoal.applydeadline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.entitylayer.DeadlineUI
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_deadline.view.*

class DeadlineAdapter : BaseAdapter<DeadlineUI, DeadlineAdapter.VH>() {

    lateinit var onClick: (position: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_deadline, parent, false))

    override fun getItemCount() = godList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val deadline = godList[position]
        holder.view.tvDeadline.text = deadline.text
        holder.view.mcvDeadline.isChecked = deadline.isSelected
    }

    fun select(position: Int) {
        godList.forEach { it.isSelected = false }
        godList[position].isSelected = true
        notifyDataSetChanged()
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setSingleClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setSingleClickListener
                onClick.invoke(adapterPosition)
            }
        }
    }
}