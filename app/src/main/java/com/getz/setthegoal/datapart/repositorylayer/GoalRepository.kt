package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.core.BaseRepository
import com.getz.setthegoal.datapart.datasource.IGoalDS
import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository
import com.google.firebase.auth.FirebaseAuth

class GoalRepository(
    private val goalDS: IGoalDS,
    private val auth: FirebaseAuth,
    private val domainToDataGoalMapper: Gandalf<Goal, GoalDto>,
    private val dataToDomainGoalMapper: Gandalf<List<GoalDto>, List<Goal>>
) : BaseRepository(), IGoalRepository {

    override suspend fun createGoal(goal: Goal, onResult: suspend (Boolean) -> Unit) {
        goalDS.createGoal(domainToDataGoalMapper.transform(goal)) { success ->
            onResult(success)
        }
    }

    override suspend fun updateGoal(goal: Goal, onResult: suspend (Boolean) -> Unit) {
        goalDS.updateGoal(domainToDataGoalMapper.transform(goal)) { success ->
            onResult(success)
        }
    }

    override suspend fun deleteGoal(goalId: String, onResult: suspend (Boolean) -> Unit) {
        goalDS.deleteGoal(goalId, onResult)
    }

    override suspend fun deleteAllGoals(onResult: suspend (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid ?: "no_id"
        goalDS.deleteAllGoals(uid) { success ->
            onResult(success)
        }
    }

    override suspend fun getUnfinishedGoals(onResult: suspend (List<Goal>) -> Unit) {
        val uid = auth.currentUser?.uid ?: "no_id"
        goalDS.getUnfinishedGoals(uid) {
            val mapped = dataToDomainGoalMapper.transform(it)
            onResult(mapped)
        }
    }

    override fun subscribeOnGoals(onResult: (List<Goal>) -> Unit) {
        val uid = auth.currentUser?.uid ?: "no_id"
        goalDS.subscribeOnGoals(uid) {
            val mapped = dataToDomainGoalMapper.transform(it)
            onResult(mapped)
        }

    }
}