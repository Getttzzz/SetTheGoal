package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.entitylayer.Goal

interface IGoalRepository {
    suspend fun createGoal(goal: Goal, onResult: suspend (Boolean) -> Unit)
    suspend fun updateGoal(goal: Goal, onResult: suspend (Boolean) -> Unit)
    suspend fun deleteGoal(goalId: String, onResult: suspend (Boolean) -> Unit)
    suspend fun getUnfinishedGoals(onResult: suspend (List<Goal>) -> Unit)
    fun subscribeOnGoals(onResult: (List<Goal>) -> Unit)
}