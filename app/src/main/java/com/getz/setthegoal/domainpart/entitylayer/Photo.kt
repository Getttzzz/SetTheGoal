package com.getz.setthegoal.domainpart.entitylayer


data class Photo(
    val urls: UrlsUI,
    val incrementDownloadLink: String,
    val userName: String,
    val profileLink: String
)

data class UrlsUI(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)