package com.getz.setthegoal.presentationpart.feature.viewgoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.util.getHideableListener
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_goal.view.*

class GoalAdapter : BaseAdapter<GoalUI, GoalAdapter.VH>() {

    lateinit var onClick: (position: Int) -> Unit
    lateinit var onOptionsClick: (position: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false))

    override fun getItemCount() = godList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val goal = godList[position]

        GlideApp.with(holder.view)
            .load(goal.photo?.urls?.small ?: "")
            .apply(RequestOptions().centerCrop())
            .listener(getHideableListener(holder.view.pbPhotoGoal))
            .error(R.drawable.layer_list_bee)
            .into(holder.view.ivPhotoGoal)

        holder.view.tvGoal.text = goal.text
        holder.view.tvSubGoalsDone.isVisible = goal.subGoals.isNotEmpty()
        val subGoalsDoneCount = goal.subGoals.filter { it.done }.size
        holder.view.tvSubGoalsDone.text = holder.view.context.getString(
            R.string.sub_goals_done,
            subGoalsDoneCount,
            goal.subGoals.size
        )
    }

    fun removeOneItem(position: Int) {
        godList.remove(godList[position])
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, godList.size)
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setSingleClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setSingleClickListener
                onClick(adapterPosition)
            }
            view.ivOptions.setSingleClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setSingleClickListener
                onOptionsClick(adapterPosition)
            }
        }
    }
}