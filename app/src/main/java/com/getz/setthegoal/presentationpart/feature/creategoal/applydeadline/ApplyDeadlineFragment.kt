package com.getz.setthegoal.presentationpart.feature.creategoal.applydeadline

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.entitylayer.DeadlineUI
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import kotlinx.android.synthetic.main.fragment_apply_deadline.*
import org.kodein.di.generic.instance
import org.kodein.di.generic.on

class ApplyDeadlineFragment : BaseFragment(R.layout.fragment_apply_deadline) {
    val vm: CreateGoalVM by kodein.on(context = this).instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("GETTTZZZ.ApplyDeadlineFragment.onCreate ---> vm.hashCode=${vm.hashCode()}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deadlineAdapter = setupDeadlineAdapter()
        val deadlines = DeadlineUI.generateDeadlines(context!!)
        deadlineAdapter.replace(deadlines)
        vm.selectedDeadline = ""
    }

    override fun onResume() {
        super.onResume()
        lottieWaiting.playAnimation()
        vm.validateDeadline()
    }

    override fun onPause() {
        super.onPause()
        lottieWaiting.pauseAnimation()
    }

    private fun setupDeadlineAdapter() = DeadlineAdapter().apply {
        onClick = { position ->
            this.select(position)
            vm.selectedDeadline = DeadlineUI.getEnumByPosition(position).timeRange
            vm.validateDeadline()
        }
        rvDeadline.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvDeadline.adapter = this
    }


}