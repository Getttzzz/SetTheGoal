package com.getz.setthegoal.presentationpart.entitylayer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class GoalUI(
    val goalId: String,
    val text: String,
    val photo: PhotoUI?,
    val subGoals: List<SubGoalUI>,
    val deadline: String,
    val worry: String,
    val forWhom: String,
    var done: Boolean,
    val createdAt: Date,
    val updatedAt: Date
) : Parcelable