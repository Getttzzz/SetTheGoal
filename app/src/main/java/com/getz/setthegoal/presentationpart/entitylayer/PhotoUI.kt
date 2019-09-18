package com.getz.setthegoal.presentationpart.entitylayer

data class PhotoUI(
    val urls: UrlsUI,
    val incrementDownloadLink: String,
    val userName: String,
    val profileLink: String,
    var isSelected: Boolean
)

data class UrlsUI(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)