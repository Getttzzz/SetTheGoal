package com.getz.setthegoal.presentationpart.feature.viewgoal

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.getz.setthegoal.R
import kotlinx.android.synthetic.main.fragment_goals_for_someone.*

class GoalsForFamilyFragment : BaseGoalsForSomeone() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvGoalsFor.text = getString(R.string.goals_for_my_family)
        vm.goalsForFamilyLD.observe(this, Observer { goals ->
            goalAdapter.replace(goals)
        })
        vm.loadGoals()
    }
}