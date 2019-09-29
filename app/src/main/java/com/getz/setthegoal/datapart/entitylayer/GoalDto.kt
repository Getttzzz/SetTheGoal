package com.getz.setthegoal.datapart.entitylayer

import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.SubGoal

/**
 * This class is used as Firestore model.
 * So, to parse firestore hash map to the model,class should have empty constructor.
 * To provide empty constructor in data class it needs to set default values for each parameter.
 * */
data class GoalDto(
    val ownerId: String = "",
    val text: String = "",
    val photo: Photo? = null,
    val subGoals: List<SubGoal> = arrayListOf(),
    val deadline: String = "",
    val forWhom: String = "",
    val done: Boolean = false
)