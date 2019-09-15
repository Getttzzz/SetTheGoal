package com.getz.setthegoal.domainpart.entitylayer

import com.getz.setthegoal.datapart.entitylayer.PartOfSpeechEnum

data class Word(
    val originalWord: String,
    val partOfSpeech: PartOfSpeechEnum
)