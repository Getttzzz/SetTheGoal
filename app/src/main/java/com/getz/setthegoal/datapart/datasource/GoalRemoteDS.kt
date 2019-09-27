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
        onResult(task.isSuccessful)
    }

    override suspend fun getGoals(): List<GoalDto> {

//        firestore.collection(COLLECTION_GOALS)
//            .addSnapshotListener { snapshot, exception ->
//                snapshot?.
//            }


        return emptyList()
    }
}