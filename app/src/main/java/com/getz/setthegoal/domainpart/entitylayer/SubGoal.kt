package com.getz.setthegoal.domainpart.entitylayer

/**
 * This class is used as Firestore model.
 * So, to parse firestore hash map to the model,class should have empty constructor.
 * To provide empty constructor in data class it needs to set default values for each parameter.
 * */
data class SubGoal(
    var goal: String = "",
    var order: Int = -1,
    var done: Boolean = false
)