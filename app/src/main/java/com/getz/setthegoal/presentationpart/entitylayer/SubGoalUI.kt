package com.getz.setthegoal.presentationpart.entitylayer

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class SubGoalUI(var goal: String = "", var order: Int, var done: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(goal)
        parcel.writeInt(order)
        parcel.writeByte(if (done) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubGoalUI> {
        override fun createFromParcel(parcel: Parcel): SubGoalUI {
            return SubGoalUI(parcel)
        }

        override fun newArray(size: Int): Array<SubGoalUI?> {
            return arrayOfNulls(size)
        }
    }
}