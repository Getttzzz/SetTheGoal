package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.GoalDto

interface IGoalDS {
    suspend fun createGoal(goalDto: GoalDto, onResult: suspend (Boolean) -> Unit)
    fun getGoals(uid: String, onResult: (List<GoalDto>) -> Unit)
//    suspend fun updateGoal()
//    suspend fun deleteGoal()
}