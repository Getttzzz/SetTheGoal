package com.getz.setthegoal.presentationpart.feature.creategoal

interface CreateGoalBridge {
    fun closeCreateFragment()
    fun scrollToAppropriateTab(isForFamily: Boolean)
}