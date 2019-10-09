package com.getz.setthegoal.presentationpart.feature.viewgoals

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_goals_for_someone.*

abstract class BaseGoalsForSomeone : BaseFragment(R.layout.fragment_goals_for_someone) {

    abstract val vm: GoalsVM
    lateinit var viewGoalBridge: ViewAllGoalsBridge
    val goalAdapter: GoalAdapter by lazy { setupGoalAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewGoalBridge = context as ViewAllGoalsBridge
    }

    private fun setupGoalAdapter() = GoalAdapter().apply {
        rvGoalsFor.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvGoalsFor.adapter = this
        onClick = { position ->
            val goalUI = this.godList[position]
            viewGoalBridge.wantToSeeObjective(goalUI)
        }
        onOptionsClick = { position ->
            val goalUI = this.godList[position]
            if (goalUI.goalId.isNotEmpty()) {
                vm.deleteGoal(goalUI.goalId)
            }
        }
    }

}