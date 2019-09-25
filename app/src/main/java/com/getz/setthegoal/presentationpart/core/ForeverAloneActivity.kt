package com.getz.setthegoal.presentationpart.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalFragment
import com.getz.setthegoal.presentationpart.feature.viewgoal.GoalsBridge
import com.getz.setthegoal.presentationpart.feature.viewgoal.GoalsFragment

class ForeverAloneActivity :
    AppCompatActivity(R.layout.activity_forever_alone),
    GoalsBridge {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, GoalsFragment(), "goalsFragment")
            .commit()
    }

    override fun openCreateGoalScreen(isForFamily: Boolean) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flMain, CreateGoalFragment.getInstance(isForFamily), "createGoalFragment")
            .addToBackStack(null) //possibility to return back to GoalsFragment()
            .commit()
    }
}
