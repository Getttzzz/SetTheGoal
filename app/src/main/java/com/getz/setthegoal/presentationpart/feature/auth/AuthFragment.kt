package com.getz.setthegoal.presentationpart.feature.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAuthFragment
import com.getz.setthegoal.presentationpart.util.gone
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.visible
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseAuthFragment(R.layout.fragment_auth) {

    private lateinit var bridge: AuthBridge

    override val onSignedInSuccessfully: () -> Unit = {
        println("GETTTZZZ.AuthFragment --->  bridge.openMainScreenAfterAuth()")
        bridge.openMainScreenAfterAuth()
    }

    override val onLastSignedInUserDetected: (String) -> Unit = { email ->
        tvGoogleAccountInfo.visible()
        btnGoogleSignOut.visible()
        tvGoogleAccountInfo.text =
            getString(R.string.looks_like_you_were_here_under_google, email)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as AuthBridge
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnGoogle.setSingleClickListener {
            println("GETTTZZZ.AuthFragment.onViewCreated --->  requestGoogleSignIn() ")
            requestGoogleSignIn()
        }
        btnIncognito.setSingleClickListener { makeSignInIncognito() }
        btnGoogleSignOut.setSingleClickListener {
            makeSignOutFromGoogle { btnGoogleSignOut.gone(); tvGoogleAccountInfo.gone() }
        }
    }
}