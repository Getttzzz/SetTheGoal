package com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_sub_goal.view.*

class SubGoalAdapter : BaseAdapter<SubGoalUI, SubGoalAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_sub_goal, parent, false))

    override fun getItemCount() = godList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val subGoal = godList[position]
        holder.view.tvNumber.text = "${subGoal.order}."
        holder.view.etSubGoal.setText(subGoal.goal)
    }

    fun addOneItem(item: SubGoalUI) {
        godList.add(item)
        notifyItemInserted(godList.size)
    }

    fun removeOneItem(position: Int) {
        godList.remove(godList[position])
        for (i in position until godList.size) godList[i].order--
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, godList.size)
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.ivRemove.setSingleClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setSingleClickListener
                removeOneItem(adapterPosition)
            }
        }
    }
}