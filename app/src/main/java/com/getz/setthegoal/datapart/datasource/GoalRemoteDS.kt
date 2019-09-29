package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

const val COLLECTION_GOALS = "goals"
const val OWNER_ID = "ownerId"

class GoalRemoteDS(
    private val firestore: FirebaseFirestore
) : IGoalDS {

    override suspend fun createGoal(goalDto: GoalDto, onResult: suspend (Boolean) -> Unit) {
        val task = firestore.collection(COLLECTION_GOALS).add(goalDto)
        Tasks.await(task)
        onResult(task.isSuccessful)
    }

    override fun getGoals(uid: String, onResult: (List<GoalDto>) -> Unit) {
        firestore.collection(COLLECTION_GOALS)
            .whereEqualTo(OWNER_ID, uid)
            .addSnapshotListener { querySnapshot, e ->
                val result = arrayListOf<GoalDto>()

                querySnapshot?.let {
                    it.documents.forEach {
                        val goalDto = it.toObject(GoalDto::class.java)
                        println("GETTTZZZ.GoalRemoteDS.getGoals ---> goalDto=$goalDto")

                        goalDto?.let { result.add(it) }
                    }
                }

                onResult(result)
            }

    }
}