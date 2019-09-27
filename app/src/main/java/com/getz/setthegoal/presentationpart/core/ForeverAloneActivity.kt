package com.getz.setthegoal.presentationpart.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.getz.setthegoal.R
import com.getz.setthegoal.di.EXTRA_ONLINE_STATUS
import com.getz.setthegoal.di.GET_CONNECTION_STATUS_ACTION
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
import kotlinx.android.synthetic.main.activity_forever_alone.*

class ForeverAloneActivity :
    AppCompatActivity(R.layout.activity_forever_alone),
    GoalsBridge, WelcomeBridge, AuthBridge, ProfileBridge, CreateGoalBridge {

    private lateinit var connectionBroReceiver: BroadcastReceiver

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

    override fun onResume() {
        super.onResume()
        val (connectionReceiver, filter) = createConnectionReceiver()
        connectionBroReceiver = connectionReceiver
        registerReceiver(connectionReceiver, filter)
    }

    override fun onPause() {
        unregisterReceiver(connectionBroReceiver)
        super.onPause()
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

    private fun createConnectionReceiver(): Pair<BroadcastReceiver, IntentFilter> {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                val isOnline = intent?.getBooleanExtra(EXTRA_ONLINE_STATUS, false) ?: false
                tvConnectionStatus?.let { tv -> tv.isVisible = !isOnline }
            }
        }
        val connectionFilter = IntentFilter(GET_CONNECTION_STATUS_ACTION)
            .apply { addCategory(Intent.CATEGORY_DEFAULT) }

        return Pair(receiver, connectionFilter)
    }
}
