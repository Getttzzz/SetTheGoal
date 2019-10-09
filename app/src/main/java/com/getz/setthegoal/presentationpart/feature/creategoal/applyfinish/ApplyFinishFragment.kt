package com.getz.setthegoal.presentationpart.feature.creategoal.applyfinish

import android.os.Bundle
import android.view.View
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import kotlinx.android.synthetic.main.fragment_apply_finish.*
import org.kodein.di.generic.instance
import org.kodein.di.generic.on

class ApplyFinishFragment : BaseFragment(R.layout.fragment_apply_finish) {

    val vm: CreateGoalVM by kodein.on(context = this).instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val motivationEnum = MotivationAnimationEnum.getRandomEnum()

        lottieGoalIsReady.repeatCount = 7
        lottieGoalIsReady.setAnimation(motivationEnum.rawRes)
        tvLottieDescription.text = getString(motivationEnum.strRes)
    }

    override fun onResume() {
        super.onResume()
        lottieGoalIsReady.playAnimation()
    }

    override fun onPause() {
        super.onPause()
        lottieGoalIsReady.pauseAnimation()
    }
}