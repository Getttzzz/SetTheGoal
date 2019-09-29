package com.getz.setthegoal.domainpart.entitylayer

/**
 * This class is used as Firestore model.
 * So, to parse firestore hash map to the model,class should have empty constructor.
 * To provide empty constructor in data class it needs to set default values for each parameter.
 * */
data class Photo(
    val urls: UrlsDomain = UrlsDomain("", "", "", "", ""),
    val incrementDownloadLink: String = "",
    val userName: String = "",
    val profileLink: String = ""
)

/**
 * This class is used as Firestore model.
 * So, to parse firestore hash map to the model,class should have empty constructor.
 * To provide empty constructor in data class it needs to set default values for each parameter.
 * */
data class UrlsDomain(
    val raw: String = "",
    val full: String = "",
    val regular: String = "",
    val small: String = "",
    val thumb: String = ""
)