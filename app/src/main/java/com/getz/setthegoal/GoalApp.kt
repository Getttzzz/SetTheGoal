package com.getz.setthegoal

import android.app.Application
import com.getz.setthegoal.dependencyinjection.networkModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class GoalApp() : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@GoalApp))
        import(networkModule)
    }
}