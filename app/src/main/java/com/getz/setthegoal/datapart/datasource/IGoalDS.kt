package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.GoalDto

interface IGoalDS {
    suspend fun createGoal(goalDto: GoalDto, onResult: suspend (Boolean) -> Unit)
    suspend fun getGoals(): List<GoalDto>
//    suspend fun updateGoal()
//    suspend fun deleteGoal()
}