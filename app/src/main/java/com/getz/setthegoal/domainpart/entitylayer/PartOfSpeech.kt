package com.getz.setthegoal.domainpart.entitylayer

import com.getz.setthegoal.datapart.entitylayer.PartOfSpeechEnum

//todo create perfect model for UI
data class Word(
    val originalWord: String,
    val imageQueryEngWord: String,
    val partOfSpeech: PartOfSpeechEnum
)