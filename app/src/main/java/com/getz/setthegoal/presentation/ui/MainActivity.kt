package com.getz.setthegoal.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.getz.setthegoal.R
import com.getz.setthegoal.presentation.ui.goals.GoalsFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, GoalsFragment(), "goalsFragment")
            .addToBackStack(null)
            .commit()
    }
}
