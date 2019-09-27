package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

const val COLLECTION_GOALS = "goals"

class GoalRemoteDS(
    private val firestore: FirebaseFirestore
) : IGoalDS {

    override suspend fun createGoal(goalDto: GoalDto, onResult: suspend (Boolean) -> Unit) {
        val task = firestore.collection(COLLECTION_GOALS).add(goalDto)

        Tasks.await(task)

        val goalRef = task.result
        println("GETTTZZZ.GoalRemoteDS.createGoal ---> goalRef.id=${goalRef?.id}")

        onResult(task.isSuccessful)
    }

    override suspend fun getGoals(): List<GoalDto> {
        return emptyList()
    }
}