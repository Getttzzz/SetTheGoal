package com.getz.setthegoal.presentationpart.feature.creategoal.applydeadline

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.entitylayer.DeadlineUI
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import kotlinx.android.synthetic.main.fragment_apply_deadline.*
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

        val deadlineAdapter = setupDeadlineAdapter()
        val deadlines = DeadlineUI.generateDeadlines(context!!)
        deadlineAdapter.replace(deadlines)
    }

    private fun setupDeadlineAdapter(): DeadlineAdapter {
        val deadlineAdapter = DeadlineAdapter()
            .apply {
                onClick = { position ->
                    this.select(position)
                    vm.selectedDeadline = DeadlineUI.getEnumByPosition(position).timeRange
                }
            }
        rvDeadline.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvDeadline.adapter = deadlineAdapter
        return deadlineAdapter
    }
}