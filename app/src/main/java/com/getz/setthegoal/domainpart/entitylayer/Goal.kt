package com.getz.setthegoal.domainpart.entitylayer

data class Goal(
    val goalId: String,
    val text: String,
    val photo: Photo?,
    val subGoals: List<SubGoal>,
    val deadline: String,
    val forWhom: String,
    val done: Boolean
)