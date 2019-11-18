package com.getz.setthegoal.presentationpart.entitylayer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubGoalUI(var goal: String = "", var order: Int, var done: Boolean) : Parcelable