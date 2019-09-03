package com.getz.setthegoal.core

import android.app.Application

class GoalApp : Application() {


    override fun onCreate() {
        super.onCreate()

        println("GETZ.GoalApp.onCreate ---> ")
    }
}