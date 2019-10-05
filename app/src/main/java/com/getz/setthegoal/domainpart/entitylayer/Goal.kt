package com.getz.setthegoal.domainpart.entitylayer

import java.util.Date

data class Goal(
    val goalId: String,
    val text: String,
    val photo: Photo?,
    val subGoals: List<SubGoal>,
    val deadline: String,
    val forWhom: String,
    val done: Boolean,
    val createdAt: Date,
    val updatedAt: Date
)