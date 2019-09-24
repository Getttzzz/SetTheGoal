package com.getz.setthegoal.presentationpart.entitylayer

data class GoalUI(
    val text: String,
    val photo: PhotoUI,
    val subGoals: List<SubGoalUI>,
    val deadline: String,
    val isForFamily: Boolean
)