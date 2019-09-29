package com.getz.setthegoal.presentationpart.feature.viewgoal

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
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


        vm.goalsLD.observe(this, Observer { goals ->
            println("GETTTZZZ.GoalsForFamilyFragment.onViewCreated ---> goals=$goals")
        })

        println("GETTTZZZ.GoalsForFamilyFragment.onViewCreated --->  vm.loadGoals()")
        vm.loadGoals()
    }
}