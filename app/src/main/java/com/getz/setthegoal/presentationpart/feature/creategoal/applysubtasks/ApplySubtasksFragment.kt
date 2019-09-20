package com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import org.kodein.di.direct
import org.kodein.di.generic.instance

class ApplySubtasksFragment : BaseFragment(R.layout.fragment_apply_subtasks) {

    private lateinit var vm: CreateGoalVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("GETTTZZZ.ApplySubtasksFragment.onViewCreated ---> vm.isForFamily=${vm.isForFamily}")
    }

    override fun onResume() {
        super.onResume()
        println("GETTTZZZ.ApplySubtasksFragment.onResume ---> ")
    }

    override fun onPause() {
        super.onPause()
        println("GETTTZZZ.ApplySubtasksFragment.onPause ---> ")
    }
}