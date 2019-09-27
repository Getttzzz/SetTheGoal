package com.getz.setthegoal.presentationpart.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.feature.auth.AuthBridge
import com.getz.setthegoal.presentationpart.feature.auth.AuthFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalBridge
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalFragment
import com.getz.setthegoal.presentationpart.feature.profile.ProfileBridge
import com.getz.setthegoal.presentationpart.feature.profile.ProfileFragment
import com.getz.setthegoal.presentationpart.feature.viewgoal.GoalsBridge
import com.getz.setthegoal.presentationpart.feature.viewgoal.GoalsFragment
import com.getz.setthegoal.presentationpart.feature.welcome.WelcomeBridge
import com.getz.setthegoal.presentationpart.feature.welcome.WelcomeFragment
import com.google.firebase.auth.FirebaseAuth

class ForeverAloneActivity :
    AppCompatActivity(R.layout.activity_forever_alone),
    GoalsBridge, WelcomeBridge, AuthBridge, ProfileBridge, CreateGoalBridge {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser
        println("GETTTZZZ.ForeverAloneActivity.onCreate ---> currentUser=$currentUser")

        if (currentUser != null) {
            openMainScreen()
        } else {
            openWelcomeScreen()
        }
    }

    override fun onSignedOutFromProfile() {
        openAuthScreen()
    }

    override fun openProfileScreen() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flMain, ProfileFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun closeCreateFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun openCreateGoalScreen(isForFamily: Boolean) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flMain, CreateGoalFragment.getInstance(isForFamily), "createGoalFragment")
            .addToBackStack(null) //possibility to return back to GoalsFragment()
            .commit()
    }

    override fun openAuthScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, AuthFragment())
            .commit()
    }

    override fun openMainScreenAfterAuth() {
        openMainScreen()
    }

    private fun openMainScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, GoalsFragment(), "goalsFragment")
            .commit()
    }

    private fun openWelcomeScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, WelcomeFragment())
            .commit()
    }
}
