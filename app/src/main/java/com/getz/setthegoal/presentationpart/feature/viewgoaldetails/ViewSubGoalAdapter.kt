package com.getz.setthegoal.presentationpart.feature.viewgoaldetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_sub_goal.view.tvNumber
import kotlinx.android.synthetic.main.item_view_sub_goal.view.*

class ViewSubGoalAdapter : BaseAdapter<SubGoalUI, ViewSubGoalAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_view_sub_goal, parent, false))

    override fun getItemCount() = godList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val subGoal = godList[position]
        holder.view.tvNumber.text =
            holder.view.context.getString(R.string.str_with_dot, subGoal.order)
        holder.view.tvSubGoalText.text = subGoal.goal

        holder.view.mcvSubGoalView.isChecked = subGoal.done
        holder.view.cbSubGoalDone.isChecked = subGoal.done
        holder.view.mcvDone.isChecked = subGoal.done
    }

    fun checkOneItem(position: Int) {
        godList[position].done = !godList[position].done
        notifyItemChanged(position)
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setSingleClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setSingleClickListener
                checkOneItem(adapterPosition)
            }
        }
    }
}