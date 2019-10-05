package com.getz.setthegoal.presentationpart.feature.viewgoals

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer

class GoalsForMyselfFragment : BaseGoalsForSomeone() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.goalsForMyselfLD.observe(this, Observer { goals ->
            goalAdapter.replace(goals)
        })
//        vm.loadGoals()
    }

}