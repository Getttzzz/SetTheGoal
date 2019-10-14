package com.getz.setthegoal.presentationpart.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.getz.setthegoal.R
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.interactorlayer.IGetUnfinishedGoalsUC
import com.getz.setthegoal.presentationpart.core.ForeverAloneActivity
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.entitylayer.WorryEnum
import org.joda.time.DateTime
import org.joda.time.Period
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


const val GOAL_NOTIFICATION_CHANNEL_NAME = "Show unfinished goals"
const val GOAL_NOTIFICATION_CHANNEL_DESCR = "Channel to show unfinished goals"
const val GOAL_CHANNEL_ID = "GOAL_CHANNEL_1"
const val GOAL_NOTIFICATION_ID = 1
const val GOAL_PENDING_INTENT_REQUEST_CODE = 327
const val EXTRA_IS_FROM_NOTIFICATION = "EXTRA_IS_FROM_NOTIFICATION"
const val EXTRA_GOALS_FOR_TODAY = "EXTRA_GOALS_FOR_TODAY"

class SendNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KodeinAware {

    override val kodein: Kodein by kodein(applicationContext)
    private val getUnfinishedGoalsUC: IGetUnfinishedGoalsUC by instance()
    private val domainToPresentationMapper: Gandalf<List<Goal>, List<GoalUI>> by instance()

    init {
        println("GETTTZZZ.SendNotificationWorker.init ---> this.hashCode=${this.hashCode()}")
    }


    /**
     * One week -> ONCE_A_DAY or ONCE_EVERY_THREE_DAYS (2 or 7 notifs)
     * One month -> ONCE_EVERY_THREE_DAYS or ONCE_EVERY_WEEK (4 or 10 notifs)
     * 3 month -> ONCE_EVERY_WEEK or ONCE_EVERY_MONTH (3 or 12 notifs)
     * 6 month -> ONCE_EVERY_MONTH or ONCE_EVERY_TWO_MONTHS (3 or 6 notifs)
     * 1 year -> ONCE_EVERY_MONTH or ONCE_EVERY_TWO_MONTHS (6 or 12 notifs)
     *
     * Example:
     *
     * (logo) YouTube Music
     * Wish you were here...
     * Pink Floyd
     *
     *
     * createdAtDay = 8 oct
     * interval = once every 3 days
     * duration = 1 week
     * deadlineDay = 15 oct
     *
     * (1----6-----12-14---18-----24) 8 oct
     * (1----6-----12-----18-----24) 9 oct
     * (1----6-----12-----18-----24) 10 oct
     * (1----6-8---12-14---18-----24) 11 oct
     * (1----6-----12-----18-----24) 12 oct Today
     * (1----6-----12-----18-----24) 13 oct
     * (1----6-----12-----18-----24) 14 oct
     * (1----6-----12-14---18-----24) 15 oct
     *
     * */
    override suspend fun doWork() = try {
        val goalsToShow = arrayListOf<Goal>()

        getUnfinishedGoalsUC.invoke(Unit, {
            Result.failure()
        }, { goals ->
            goals.forEach { goal ->
                //check each goal, whether current day suits to show a notification with this goal.
                //createdAt plus interval

                when (WorryEnum.getEnumByWorryName(goal.worry)) {
                    WorryEnum.ONCE_A_DAY -> goalsToShow.add(goal)
                    WorryEnum.ONCE_EVERY_THREE_DAYS -> calculateInterval(3, goal, goalsToShow)
                    WorryEnum.ONCE_EVERY_WEEK -> calculateInterval(7, goal, goalsToShow)
                    WorryEnum.ONCE_EVERY_MONTH -> calculateInterval(31, goal, goalsToShow)
                    WorryEnum.ONCE_EVERY_TWO_MONTHS -> calculateInterval(62, goal, goalsToShow)
                }
            }

            if (goalsToShow.isNotEmpty()) {
                val title = applicationContext.resources.getQuantityString(
                    R.plurals.goals_for_notification_plurals,
                    goalsToShow.size
                )
                val msg = buildString {
                    append(goalsToShow[0].text)
                    if (goalsToShow.size > 1) append(" ").append(applicationContext.getString(R.string.and_others))
                }
                val mapped = ArrayList(domainToPresentationMapper.transform(goalsToShow))
                showGoalNotification(title, msg, mapped, applicationContext)
            }
        })

        println("GETTTZZZ.SendNotificationWorker.doWork ---> Result.success() goalsToShow=$goalsToShow")
        Result.success()
    } catch (e: Exception) {
        println("GETTTZZZ.SendNotificationWorker.doWork ---> Result.failure() error=$e")
        e.printStackTrace()
        Result.failure()
    }

    private fun calculateInterval(eachDay: Int, goal: Goal, goalsToShow: ArrayList<Goal>) {
        //get diff between Today and createdAtDay
        //12-8=4
        //4 mod 3 == 1
        val period = Period(DateTime(goal.createdAt), DateTime.now())
        val diffDays = period.days
        val reminder = diffDays.rem(eachDay)
        println("GETTTZZZ.SendNotificationWorker.doWork ---> diffDays=$diffDays")
        println("GETTTZZZ.SendNotificationWorker.doWork ---> reminder=$reminder")
        if (reminder == 0) goalsToShow.add(goal)
    }

    private fun showGoalNotification(
        title: String,
        message: String,
        goals: ArrayList<GoalUI>,
        context: Context
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = GOAL_NOTIFICATION_CHANNEL_NAME
            val description = GOAL_NOTIFICATION_CHANNEL_DESCR
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(GOAL_CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        val intentMainActivity =
            Intent(applicationContext, ForeverAloneActivity::class.java).apply {
                putExtra(EXTRA_IS_FROM_NOTIFICATION, true)
                putParcelableArrayListExtra(EXTRA_GOALS_FOR_TODAY, goals)
            }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            GOAL_PENDING_INTENT_REQUEST_CODE,
            intentMainActivity,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, GOAL_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bee_app_icon)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(context).notify(GOAL_NOTIFICATION_ID, builder.build())
    }
}