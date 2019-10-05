package com.getz.setthegoal.presentationpart.entitylayer

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class GoalUI(
    val goalId: String,
    val text: String,
    val photo: PhotoUI?,
    val subGoals: List<SubGoalUI>,
    val deadline: String,
    val forWhom: String,
    var done: Boolean,
    val createdAt: Date,
    val updatedAt: Date
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(PhotoUI::class.java.classLoader),
        parcel.createTypedArrayList(SubGoalUI),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readSerializable() as Date,
        parcel.readSerializable() as Date
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(goalId)
        parcel.writeString(text)
        parcel.writeParcelable(photo, flags)
        parcel.writeTypedList(subGoals)
        parcel.writeString(deadline)
        parcel.writeString(forWhom)
        parcel.writeByte(if (done) 1 else 0)
        parcel.writeSerializable(createdAt)
        parcel.writeSerializable(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GoalUI> {
        override fun createFromParcel(parcel: Parcel): GoalUI {
            return GoalUI(parcel)
        }

        override fun newArray(size: Int): Array<GoalUI?> {
            return arrayOfNulls(size)
        }
    }

}