package com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_apply_sub_goal.*
import org.kodein.di.generic.instance
import org.kodein.di.generic.on

class ApplySubGoalFragment : BaseFragment(R.layout.fragment_apply_sub_goal) {

    val vm: CreateGoalVM by kodein.on(context = this).instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subGoalAdapter = setupSubGoalAdapter()

        btnAddSubGoal.setSingleClickListener(300) {
            subGoalAdapter.addOneItem(SubGoalUI("", subGoalAdapter.godList.size + 1, false))
        }

        vm.selectedSubTasks = subGoalAdapter.godList
    }

    override fun onResume() {
        super.onResume()
        vm.validateSubGoal()
    }

    private fun setupSubGoalAdapter() = SubGoalAdapter().apply {
        rvSubGoal.setHasFixedSize(true)
        rvSubGoal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvSubGoal.adapter = this
    }
}