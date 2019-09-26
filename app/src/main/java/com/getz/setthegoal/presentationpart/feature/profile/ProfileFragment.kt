package com.getz.setthegoal.presentationpart.feature.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAuthFragment
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.feature.creategoal.applyfinish.MotivationAnimationEnum
import com.getz.setthegoal.presentationpart.util.gone
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.visible
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseAuthFragment(R.layout.fragment_profile) {

    private lateinit var bridge: ProfileBridge

    override val onSignedInSuccessfully: () -> Unit = {
        getUser()?.let {
            setupForGoogleUser(it)
            btnBecomeRealUser.gone()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as ProfileBridge
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUser()?.let { user ->
            println("GETTTZZZ.ProfileFragment.onViewCreated ---> user.photoUrl=${user.photoUrl}")
            if (user.isAnonymous) {
                setupForIncognitoUser()
            } else {
                setupForGoogleUser(user)
            }
        }
        btnSignOut.setSingleClickListener {
            makeSignOutFromFirebase()
            bridge.onSignedOutFromProfile()
        }
        btnBecomeRealUser.setSingleClickListener { requestGoogleSignInWhenIncognito() }
        setupLottie()
    }

    private fun setupLottie() {
        val randomEnum = MotivationAnimationEnum.getRandomEnum()
        lottieAbstraction.setAnimation(randomEnum.rawRes)
        lottieAbstraction.repeatCount = 10
    }

    private fun setupForGoogleUser(user: FirebaseUser) {
        tvAccountInfoProfile.text = buildSpannedString {
            append(getString(R.string.you_are_signed_in_as))
            append("\n")
            bold { append(user.email) }
        }

        GlideApp.with(this)
            .load(user.photoUrl)
            .error(R.drawable.ic_person)
            .apply(RequestOptions().circleCrop())
            .into(ivAvatarProfile)
    }

    private fun setupForIncognitoUser() {
        btnBecomeRealUser.visible()
        tvAccountInfoProfile.text = buildSpannedString {
            append(getString(R.string.you_are_signed_in_as))
            append("\n")
            bold { append(getString(R.string.incognito)) }
        }
    }

    override fun onResume() {
        super.onResume()
        lottieAbstraction.playAnimation()
    }

    override fun onPause() {
        super.onPause()
        lottieAbstraction.pauseAnimation()
    }
}