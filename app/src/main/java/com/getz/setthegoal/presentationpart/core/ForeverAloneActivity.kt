package com.getz.setthegoal.presentationpart.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.feature.goals.GoalsFragment

class ForeverAloneActivity : AppCompatActivity(R.layout.activity_forever_alone) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, GoalsFragment(), "goalsFragment")
            .addToBackStack(null)
            .commit()
    }
}
