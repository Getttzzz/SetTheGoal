package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.core.BaseRepository
import com.getz.setthegoal.datapart.datasource.IGoalDS
import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository
import com.google.firebase.auth.FirebaseAuth

class GoalRepository(
    private val remoteDS: IGoalDS,
    private val auth: FirebaseAuth,
    private val domainToDataGoalMapper: Gandalf<Goal, GoalDto>,
    private val dataToDomainGoalMapper: Gandalf<List<GoalDto>, List<Goal>>
) : BaseRepository(), IGoalRepository {

    override suspend fun createGoal(goal: Goal, onResult: suspend (Boolean) -> Unit) {
        remoteDS.createGoal(domainToDataGoalMapper.transform(goal)) { success ->
            onResult(success)
        }
    }

    override fun getGoals(onResult: (List<Goal>) -> Unit) {
        val uid = auth.currentUser?.uid ?: "no_id"
        remoteDS.getGoals(uid) {
            val mapped = dataToDomainGoalMapper.transform(it)
            onResult(mapped)
        }

    }
}