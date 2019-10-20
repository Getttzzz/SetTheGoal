package com.getz.setthegoal.presentationpart.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.getz.setthegoal.R
import com.getz.setthegoal.di.EXTRA_ONLINE_STATUS
import com.getz.setthegoal.di.GET_CONNECTION_STATUS_ACTION
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.feature.auth.AuthBridge
import com.getz.setthegoal.presentationpart.feature.auth.AuthFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalBridge
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalFragment
import com.getz.setthegoal.presentationpart.feature.prewelcome.PreWelcomeBridge
import com.getz.setthegoal.presentationpart.feature.prewelcome.PreWelcomeFragment
import com.getz.setthegoal.presentationpart.feature.profile.ProfileBridge
import com.getz.setthegoal.presentationpart.feature.profile.ProfileFragment
import com.getz.setthegoal.presentationpart.feature.viewgoaldetails.ViewGoalBridge
import com.getz.setthegoal.presentationpart.feature.viewgoaldetails.ViewGoalFragment
import com.getz.setthegoal.presentationpart.feature.viewgoals.GoalsBridge
import com.getz.setthegoal.presentationpart.feature.viewgoals.GoalsFragment
import com.getz.setthegoal.presentationpart.feature.viewgoals.ViewAllGoalsBridge
import com.getz.setthegoal.presentationpart.feature.welcome.WelcomeBridge
import com.getz.setthegoal.presentationpart.feature.welcome.WelcomeFragment
import com.getz.setthegoal.presentationpart.feature.wordbyword.WordByWordBridge
import com.getz.setthegoal.presentationpart.feature.wordbyword.WordByWordFragment
import com.getz.setthegoal.presentationpart.workmanager.EXTRA_GOALS_FOR_TODAY
import com.getz.setthegoal.presentationpart.workmanager.EXTRA_IS_FROM_NOTIFICATION
import com.getz.setthegoal.presentationpart.workmanager.SendNotificationWorker
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forever_alone.*
import org.joda.time.DateTime
import org.joda.time.Duration
import java.util.concurrent.TimeUnit

const val TAG_GOALS_FRAGMENT = "TAG_GOALS_FRAGMENT"
const val TAG_CREATE_GOAL_FRAGMENT = "TAG_CREATE_GOAL_FRAGMENT"
const val TAG_PERIODIC_SEND_NOTIFICATION_WORKER = "TAG_PERIODIC_SEND_NOTIFICATION_WORKER"
const val TAG_SEND_NOTIFICATION_UNIQUE_NAME = "TAG_SEND_NOTIFICATION_UNIQUE_NAME"

const val REMINDER_AT_HOUR_AM = 8

class ForeverAloneActivity :
    AppCompatActivity(R.layout.activity_forever_alone),
    GoalsBridge, WelcomeBridge, AuthBridge, ProfileBridge, CreateGoalBridge, ViewAllGoalsBridge,
    ViewGoalBridge, WordByWordBridge, PreWelcomeBridge {

    private lateinit var connectionBroReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val isFromNotification = intent.getBooleanExtra(EXTRA_IS_FROM_NOTIFICATION, false)
            if (isFromNotification) {
                val goals = intent?.getParcelableArrayListExtra(EXTRA_GOALS_FOR_TODAY)
                    ?: emptyList<GoalUI>()
                openWordByWordScreen(goals)
            } else {
                scheduleEverydayWorker()
                openMainScreen()
            }
        } else {
            openWelcomeScreen()
        }
    }

    /**
     * Need if user clicks on notification when app is opened.
     * */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val isFromNotification = intent?.getBooleanExtra(EXTRA_IS_FROM_NOTIFICATION, false) ?: false
        val goals = intent?.getParcelableArrayListExtra(EXTRA_GOALS_FOR_TODAY)
            ?: emptyList<GoalUI>()

        if (isFromNotification) openWordByWordScreen(goals)
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

    private fun openWordByWordScreen(goals: List<GoalUI>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, WordByWordFragment.getInstance(goals))
            .commit()
    }

    override fun closeWordByWordScreen() {
        openMainScreen()
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

    override fun wantToSeeObjective(goal: GoalUI) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flMain, ViewGoalFragment.getInstance(goal))
            .addToBackStack(null)
            .commit()
    }

    override fun closeCreateFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun closeViewGoalScreen() {
        supportFragmentManager.popBackStack()
    }

    override fun scrollToAppropriateTab(who: String) {
        val goalsFragment =
            supportFragmentManager.findFragmentByTag(TAG_GOALS_FRAGMENT) as? GoalsFragment
        goalsFragment?.scrollToAppropriateTab(who)
    }

    override fun openCreateGoalScreen() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flMain, CreateGoalFragment.getInstance(), TAG_CREATE_GOAL_FRAGMENT)
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
            .replace(R.id.flMain, GoalsFragment(), TAG_GOALS_FRAGMENT)
            .commit()
    }

    override fun closePreWelcomeScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, WelcomeFragment())
            .commit()
    }

    private fun openWelcomeScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flMain, PreWelcomeFragment())
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

    private fun scheduleEverydayWorker() {
        println("GETTTZZZ.ForeverAloneActivity.scheduleEverydayWorker ---> DateTime.now().hourOfDay=${DateTime.now().hourOfDay}")

        val duration = if (DateTime.now().hourOfDay > REMINDER_AT_HOUR_AM)
            Duration(
                DateTime.now(),
                DateTime.now().withTimeAtStartOfDay().plusHours(REMINDER_AT_HOUR_AM)
            )
        else
            Duration(
                DateTime.now(),
                DateTime.now().withTimeAtStartOfDay().plusDays(1).plusHours(REMINDER_AT_HOUR_AM)
            )

        println("GETTTZZZ.ForeverAloneActivity.scheduleEverydayWorker ---> delayHours=${duration.standardHours} delayMinutes=${duration.standardMinutes}")

        val periodicRequest = PeriodicWorkRequestBuilder<SendNotificationWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS,
            flexTimeInterval = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
            flexTimeIntervalUnit = TimeUnit.MILLISECONDS
        )
            .setInitialDelay(duration.standardMinutes, TimeUnit.MINUTES)
            .addTag(TAG_PERIODIC_SEND_NOTIFICATION_WORKER)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                TAG_SEND_NOTIFICATION_UNIQUE_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                periodicRequest
            )

//        WorkManager.getInstance(this)
//            .getWorkInfoByIdLiveData(periodicRequest.id)
//            .observe(this, Observer { workInfo ->
//                workInfo?.let { info ->
//                    println("GETTTZZZ.ForeverAloneActivity.scheduleEverydayWorker ---> info.tags=${info.tags}")
//                    println("GETTTZZZ.ForeverAloneActivity.scheduleEverydayWorker ---> info.state=${info.state}")
//                }
//            })
    }
}
