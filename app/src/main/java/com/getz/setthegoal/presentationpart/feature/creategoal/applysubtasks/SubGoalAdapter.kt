package com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import com.getz.setthegoal.presentationpart.util.addTextListener
import com.getz.setthegoal.presentationpart.util.removeTextListener
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_sub_goal.view.*

class SubGoalAdapter : BaseAdapter<SubGoalUI, SubGoalAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_sub_goal, parent, false))

    override fun getItemCount() = godList.size

    private var magicTextWatcherMap = hashMapOf<Int, TextWatcher>()

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)

        val tw = holder.view.etSubGoal.addTextListener { godList[holder.adapterPosition].goal = it }
        magicTextWatcherMap[holder.adapterPosition] = tw
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)

        val tw = magicTextWatcherMap[holder.adapterPosition]
        holder.view.etSubGoal.removeTextListener(tw)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val subGoal = godList[position]
        holder.view.tvNumber.text =
            holder.view.context.getString(R.string.str_with_dot, subGoal.goal)
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