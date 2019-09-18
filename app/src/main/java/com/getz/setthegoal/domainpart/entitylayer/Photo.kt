package com.getz.setthegoal.domainpart.entitylayer


data class Photo(
    val urls: UrlsDomain,
    val incrementDownloadLink: String,
    val userName: String,
    val profileLink: String
)

data class UrlsDomain(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)