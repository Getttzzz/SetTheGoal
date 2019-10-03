package com.getz.setthegoal.presentationpart.entitylayer

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class PhotoUI(
    val urls: UrlsUI,
    val incrementDownloadLink: String,
    val userName: String,
    val profileLink: String,
    var isSelected: Boolean
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<UrlsUI>(UrlsUI::class.java.classLoader),
        source.readString(),
        source.readString(),
        source.readString(),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(urls, 0)
        writeString(incrementDownloadLink)
        writeString(userName)
        writeString(profileLink)
        writeInt((if (isSelected) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PhotoUI> = object : Parcelable.Creator<PhotoUI> {
            override fun createFromParcel(source: Parcel): PhotoUI = PhotoUI(source)
            override fun newArray(size: Int): Array<PhotoUI?> = arrayOfNulls(size)
        }
    }
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class UrlsUI(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(raw)
        writeString(full)
        writeString(regular)
        writeString(small)
        writeString(thumb)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UrlsUI> = object : Parcelable.Creator<UrlsUI> {
            override fun createFromParcel(source: Parcel): UrlsUI = UrlsUI(source)
            override fun newArray(size: Int): Array<UrlsUI?> = arrayOfNulls(size)
        }
    }
}