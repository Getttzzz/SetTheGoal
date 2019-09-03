package com.getz.setthegoal.ui.goals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.getz.setthegoal.R
import com.getz.setthegoal.ui.goals.pager.GoalsPagerAdapter
import kotlinx.android.synthetic.main.fragment_goals.*

class GoalsFragment : Fragment(R.layout.fragment_goals) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("GETZ.GoalsFragment.onViewCreated ---> ")

        vpGoals.adapter = GoalsPagerAdapter(childFragmentManager)
    }
}