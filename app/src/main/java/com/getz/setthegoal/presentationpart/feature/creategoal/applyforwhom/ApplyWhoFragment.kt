package com.getz.setthegoal.presentationpart.feature.creategoal.applyforwhom

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.entitylayer.WhoUI
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_apply_who.*
import org.kodein.di.generic.instance
import org.kodein.di.generic.on

class ApplyWhoFragment : BaseFragment(R.layout.fragment_apply_who) {

    val vm: CreateGoalVM by kodein.on(context = this).instance()
    val adapter: WhoAdapter by lazy { setupWhoAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.replace(WhoUI.generateForWhom(requireContext()))
        lottieWaiting.setSingleClickListener { lottieWaiting.playAnimation() }
    }

    override fun onResume() {
        super.onResume()
        lottieWaiting.playAnimation()
        vm.validateWho()
    }

    override fun onPause() {
        super.onPause()
        lottieWaiting.pauseAnimation()
    }

    private fun setupWhoAdapter() = WhoAdapter().apply {
        onClick = { position ->
            this.select(position)
            vm.who = WhoUI.getEnumByPosition(position).personName
            vm.validateWho()
        }
        rvWho.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvWho.adapter = this
    }
}