package com.getz.setthegoal.presentationpart.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.getz.setthegoal.domainpart.interactorlayer.IGetUnfinishedGoalsUC
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SendNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KodeinAware {

    override val kodein: Kodein by kodein(applicationContext)
    private val getUnfinishedGoalsUC: IGetUnfinishedGoalsUC by instance()

    init {
        println("GETTTZZZ.SendNotificationWorker.init ---> this.hashCode=${this.hashCode()}")
    }

    /**
     * Todo: send notification that You have a goal(goals)
     * Example:
     *
     * (logo) YouTube Music
     * Wish you were here...
     * Pink Floyd
     *
     *
     *
     *
     * */
    override suspend fun doWork() = try {


        getUnfinishedGoalsUC.invoke(Unit, {
            println("GETTTZZZ.SendNotificationWorker.doWork ---> getUnfinishedGoalsUC error=$it")
        }, { goals ->
            goals.forEach { goal ->
                println("GETTTZZZ.SendNotificationWorker.doWork ---> goal=$goal")
            }
        })

        //foreach goals, find which != done

        println("GETTTZZZ.SendNotificationWorker.doWork ---> Result.success()")
        Result.success()
    } catch (e: Exception) {
        println("GETTTZZZ.SendNotificationWorker.doWork ---> Result.failure() error=$e")
        e.printStackTrace()
        Result.failure()
    }

}