package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.core.BaseRepository
import com.getz.setthegoal.datapart.datasource.IGoalDS
import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository

class GoalRepository(
    private val remoteDS: IGoalDS,
    private val domainToDataGoalMapper: Gandalf<Goal, GoalDto>
) : BaseRepository(), IGoalRepository {

    override suspend fun createGoal(goal: Goal, onResult: suspend (Boolean) -> Unit) {
        remoteDS.createGoal(domainToDataGoalMapper.transform(goal)) { success ->
            onResult(success)
        }
    }
}