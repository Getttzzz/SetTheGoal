package com.getz.setthegoal.presentationpart.feature.viewgoals

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import org.kodein.di.generic.instance
import org.kodein.di.generic.on

class AchievedFragment : BaseGoalsForSomeone() {

    override val vm: GoalsVM by kodein.on(context = this).instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.achievedLD.observe(this, Observer { goals ->
            goalAdapter.replace(goals)
        })
    }
}