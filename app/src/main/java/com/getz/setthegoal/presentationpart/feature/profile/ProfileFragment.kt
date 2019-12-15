package com.getz.setthegoal.presentationpart.feature.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.work.WorkManager
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.BuildConfig
import com.getz.setthegoal.R
import com.getz.setthegoal.di.AndroidScope
import com.getz.setthegoal.domainpart.interactorlayer.IDeleteAllGoalsUC
import com.getz.setthegoal.presentationpart.core.BaseAuthFragment
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.feature.creategoal.applyfinish.MotivationAnimationEnum
import com.getz.setthegoal.presentationpart.util.gone
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.showOkOrCancelDialog
import com.getz.setthegoal.presentationpart.util.visible
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val PROFILE_VM_MODULE = "PROFILE_VM_MODULE"

fun getProfileModule() = Kodein.Module(PROFILE_VM_MODULE) {
    bind<ProfileVM>() with scoped<Fragment>(AndroidScope).singleton {
        ProfileVM.getInstance(instance(), ProfileVMF(instance()))
    }
}


class ProfileFragment : BaseAuthFragment(R.layout.fragment_profile) {

    private val vm: ProfileVM by kodein.on(context = this).instance()
    private lateinit var bridge: ProfileBridge

    override val onSignedInSuccessfully: () -> Unit = {
        getUser()?.let {
            setupForGoogleUser(it)
            btnBecomeRealUser.gone()
        }
    }

    override fun provideOverridingModule() = getProfileModule()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as ProfileBridge
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUser()
        setupListeners()
        setupSignOutBtn()
        btnBecomeRealUser.setSingleClickListener { requestGoogleSignInWhenIncognito() }
        setupVersion()
        setupLottie()
    }

    private fun setupUser() {
        getUser()?.let { user ->
            println("GETTTZZZ.ProfileFragment.onViewCreated ---> user.photoUrl=${user.photoUrl}")
            if (user.isAnonymous) {
                setupForIncognitoUser()
            } else {
                setupForGoogleUser(user)
            }
        }
    }

    private fun setupListeners() {
        vm.deleteAllGoalsFinished.observe(viewLifecycleOwner, Observer {
            getUser()?.delete() //removes anonymous account and makes sign out.
            closeProfileScreen()
        })
    }

    private fun setupSignOutBtn() {
        btnSignOut.setSingleClickListener {
            val isAnon = getUser()?.isAnonymous ?: false

            showOkOrCancelDialog(
                title = R.string.do_you_want_to_sign_out,
                description = if (isAnon) R.string.do_you_want_to_sign_out_incognito else null,
                context = requireContext()
            ) {

                WorkManager.getInstance(requireContext()).cancelAllWork()

                if (isAnon) {
                    vm.deleteAllGoals()
                } else {
                    signOutFromFirebase()
                    closeProfileScreen()
                }
            }
        }
    }

    private fun closeProfileScreen() {
        println("GETTTZZZ.ProfileFragment.closeProfileScreen ---> ")
        bridge.onSignedOutFromProfile()
    }

    private fun setupVersion() {
        val version = BuildConfig.APK_VERSION
        tvAppVersion.text = getString(R.string.made_with_love_by_yuriy_getsko, version)
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


class ProfileVM(
    private val deleteAllGoalsUC: IDeleteAllGoalsUC
) : BaseVm() {

    val deleteAllGoalsFinished = MutableLiveData<Unit>()

    fun deleteAllGoals() = launch {
        deleteAllGoalsUC.invoke(Unit, ::processError) {
            println("GETTTZZZ.ProfileVM.deleteAllGoals ---> ")
            deleteAllGoalsFinished.postValue(Unit)
        }
    }

    companion object {
        fun getInstance(fragment: Fragment, factory: ProfileVMF) =
            ViewModelProviders.of(fragment, factory)[ProfileVM::class.java]
    }
}


class ProfileVMF(
    private val deleteAllGoalsUC: IDeleteAllGoalsUC
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ProfileVM(deleteAllGoalsUC) as T
}