package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.GoalDto

interface IGoalDS {
    suspend fun createGoal(goalDto: GoalDto, onResult: suspend (Boolean) -> Unit)
    suspend fun deleteGoal(goalId: String, onResult: suspend (Boolean) -> Unit)
    suspend fun updateGoal(goalDto: GoalDto, onResult: suspend (Boolean) -> Unit)
    suspend fun getUnfinishedGoals(uid: String, onResult: suspend (List<GoalDto>) -> Unit)
    fun subscribeOnGoals(uid: String, onResult: (List<GoalDto>) -> Unit)
}