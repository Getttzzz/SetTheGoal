package com.getz.setthegoal.presentationpart.feature.prewelcome

import android.content.Context
import android.os.Bundle
import android.view.View
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_goals_for_someone.lottieBirds
import kotlinx.android.synthetic.main.fragment_pre_welcome.*

class PreWelcomeFragment : BaseFragment(R.layout.fragment_pre_welcome) {

    private lateinit var bridge: PreWelcomeBridge

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as PreWelcomeBridge
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottieBirds.speed = 0.6f
        lottieBirds.repeatCount = 3
        btnSkip.setSingleClickListener { bridge.closePreWelcomeScreen() }
    }

    override fun onResume() {
        super.onResume()
        lottieBirds.playAnimation()
    }

    override fun onPause() {
        super.onPause()
        lottieBirds.pauseAnimation()
    }
}