package com.getz.setthegoal.presentationpart.feature.viewgoals

import com.getz.setthegoal.presentationpart.entitylayer.GoalUI

interface ViewGoalBridge {
    fun wantToSeeObjective(goal: GoalUI)
}