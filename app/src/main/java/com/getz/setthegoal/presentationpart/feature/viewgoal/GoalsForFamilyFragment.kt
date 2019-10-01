package com.getz.setthegoal.presentationpart.feature.viewgoal

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_goals_for_family.*
import org.kodein.di.direct
import org.kodein.di.generic.instance

class GoalsForFamilyFragment : BaseFragment(R.layout.fragment_goals_for_family) {

    lateinit var vm: GoalsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(GoalsVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = setupGoalAdapter()

        vm.goalsLD.observe(this, Observer { goals ->
            adapter.replace(goals)
        })

        vm.loadGoals()
    }

    private fun setupGoalAdapter() = GoalAdapter().apply {
        rvGoalsForFamily.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvGoalsForFamily.adapter = this
        onClick = { position ->
            val goalUI = this.godList[position]
        }
        onOptionsClick = { position ->
            val goalUI = this.godList[position]
            if (goalUI.goalId.isNotEmpty()) {
                vm.deleteGoal(goalUI.goalId)
            }
        }
    }

}