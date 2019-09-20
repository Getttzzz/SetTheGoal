package com.getz.setthegoal.presentationpart.feature.creategoal.applydeadline

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import org.kodein.di.direct
import org.kodein.di.generic.instance

class ApplyDeadlineFragment : BaseFragment(R.layout.fragment_apply_deadline) {
    private lateinit var vm: CreateGoalVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("GETTTZZZ.ApplyDeadlineFragment.onViewCreated ---> vm.isForFamily=${vm.isForFamily}")
    }

    override fun onResume() {
        super.onResume()
        println("GETTTZZZ.ApplyDeadlineFragment.onResume ---> ")
    }

    override fun onPause() {
        super.onPause()
        println("GETTTZZZ.ApplyDeadlineFragment.onPause ---> ")
    }
}