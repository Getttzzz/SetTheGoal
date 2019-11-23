package com.getz.setthegoal.presentationpart.feature.viewgoals

import android.content.Context
import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottieBirds.speed = 0.6f
    }

    override fun onResume() {
        super.onResume()
        lottieBirds.playAnimation()
    }

    override fun onPause() {
        super.onPause()
        lottieBirds.pauseAnimation()
    }

    private fun setupGoalAdapter() = GoalAdapter().apply {
        onClick = { position ->
            val goalUI = this.godList[position]
            viewGoalBridge.wantToSeeObjective(goalUI)
        }
        //todo leave it for better times...
        onOptionsClick = { position ->
            val goalUI = this.godList[position]
            if (goalUI.goalId.isNotEmpty()) {
//                vm.deleteGoal(goalUI.goalId)
            }
        }

        rvGoalsFor.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvGoalsFor.setupEmptyView(clPlaceholderBirds)
        rvGoalsFor.adapter = this
    }

}