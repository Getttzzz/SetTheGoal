package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.entitylayer.Goal

interface IGoalRepository {
    suspend fun createGoal(goal: Goal, onResult: suspend (Boolean) -> Unit)
}