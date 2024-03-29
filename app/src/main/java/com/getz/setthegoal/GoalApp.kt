package com.getz.setthegoal

import android.app.Application
import com.getz.setthegoal.di.connectionModule
import com.getz.setthegoal.di.dataSourceModule
import com.getz.setthegoal.di.firestoreModule
import com.getz.setthegoal.di.mapperModule
import com.getz.setthegoal.di.networkModule
import com.getz.setthegoal.di.repositoryModule
import com.getz.setthegoal.di.translatorModule
import com.getz.setthegoal.di.useCaseModule
import net.danlew.android.joda.JodaTimeAndroid
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class GoalApp() : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        println("GETTTZZZ.GoalApp.onCreate ---> ")
        JodaTimeAndroid.init(this)
    }

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@GoalApp))
        import(networkModule)
        import(mapperModule)
        import(repositoryModule)
        import(dataSourceModule)
        import(useCaseModule)
        import(translatorModule)
        import(firestoreModule) //eagerSingleton (load after onCreate)
        import(connectionModule) //eagerSingleton (load after onCreate)
    }
}