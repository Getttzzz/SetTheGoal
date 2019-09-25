package com.getz.setthegoal.presentationpart.feature.welcome

import android.content.Context
import android.os.Bundle
import android.view.View
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : BaseFragment(R.layout.fragment_welcome) {

    lateinit var bridge: WelcomeBridge

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as WelcomeBridge
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSkip.setSingleClickListener { bridge.openAuthScreen() }
    }
}