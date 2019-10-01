package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.entitylayer.Goal

interface IGoalRepository {
    suspend fun createGoal(goal: Goal, onResult: suspend (Boolean) -> Unit)
    suspend fun deleteGoal(goalId: String, onResult: suspend (Boolean) -> Unit)
    fun getGoals(onResult: (List<Goal>) -> Unit)
}