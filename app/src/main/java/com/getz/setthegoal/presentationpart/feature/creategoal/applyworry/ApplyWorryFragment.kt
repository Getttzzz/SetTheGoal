package com.getz.setthegoal.presentationpart.feature.creategoal.applyworry

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.entitylayer.WorryUI
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_apply_worry.*
import org.kodein.di.generic.instance
import org.kodein.di.generic.on

class ApplyWorryFragment : BaseFragment(R.layout.fragment_apply_worry) {

    val vm: CreateGoalVM by kodein.on(context = this).instance()
    val adapter: WorryAdapter by lazy { setupWorryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottieWaiting.speed = 0.6f
        lottieWaiting.setSingleClickListener { lottieWaiting.playAnimation() }
    }

    override fun onResume() {
        super.onResume()
        adapter.replace(WorryUI.generateWorries(vm.selectedDeadline))
        lottieWaiting.playAnimation()
        vm.validateWorry()
    }

    override fun onPause() {
        super.onPause()
        lottieWaiting.pauseAnimation()
    }

    private fun setupWorryAdapter() = WorryAdapter().apply {
        onClick = { position ->
            this.select(position)
            vm.selectedWorry = WorryUI.getEnumByPosition(vm.selectedDeadline, position).worryName
            vm.validateWorry()
        }
        rvWorry.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvWorry.adapter = this
    }
}