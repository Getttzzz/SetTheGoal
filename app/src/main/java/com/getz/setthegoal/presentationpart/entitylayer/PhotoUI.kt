package com.getz.setthegoal.presentationpart.entitylayer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoUI(
    val urls: UrlsUI,
    val incrementDownloadLink: String,
    val userName: String,
    val profileLink: String,
    var isSelected: Boolean
) : Parcelable

@Parcelize
data class UrlsUI(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
) : Parcelable