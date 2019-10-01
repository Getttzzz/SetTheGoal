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
        val newDocumentRef = firestore.collection(COLLECTION_GOALS).document()
        goalDto.goalId = newDocumentRef.id
        val task = newDocumentRef.set(goalDto)
        Tasks.await(task)
        onResult(task.isSuccessful)
    }

    /**
     * Interesting fact: to delete created offline model, first of all, developer should
     * insert document ref (mean 'id') into the model. After that step, it's easy to delete
     * offline model by doc ref.
     *
     * However, Doug Stevenson (Google Developer Advocate) said:
     * There's no API for manipulating the local cache in any way.
     * Source: https://stackoverflow.com/a/48932470/12009139
     * */
    override suspend fun deleteGoal(goalId: String, onResult: suspend (Boolean) -> Unit) {
        val task = firestore.collection(COLLECTION_GOALS).document(goalId).delete()
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
                        it.toObject(GoalDto::class.java)?.let { goalDto -> result.add(goalDto) }
                    }
                }

                onResult(result)
            }

    }
}