package com.getz.setthegoal

import android.app.Application

class GoalApp : Application() {


    override fun onCreate() {
        super.onCreate()

        println("GETZ.GoalApp.onCreate ---> ")
    }
}